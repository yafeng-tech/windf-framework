package cn.windf.plugin.controller.api;

import cn.windf.core.entity.BaseEntity;
import cn.windf.core.entity.Page;
import cn.windf.core.entity.ResultData;
import cn.windf.core.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseManageControllerTest<T extends BaseEntity> {

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String getName() {
        return "";
    }

    /**
     * 准备数据
     */
    @Test
    public void t000Ready() {
        this.ready();
    }

    @Test
    public void readyData() {
        // 获取预备数据
        List<Map<String, Object>> dataList = this.getCreateData();

    }

    /**
     * 测试创建数据接口
     * 获取要创建的数据
     * 遍历进行创建
     * 每次创建完，获取数据进行验证，是否存在
     */
    @Test
    public void t101Create() {
        // 获取预备数据
        List<Map<String, Object>> dataList = this.getCreateData();

        // 依次添加
        for (Map<String, Object> data : dataList) {
            // 测试添加
            ResponseEntity<ResultData> responseEntity = restTemplate.postForEntity(this.getBasePath() + "/", data, ResultData.class);
            ResultData resultData = responseEntity.getBody();
            assertThat(resultData).as("添加数据返回不为空").isNotNull();
            assertThat(resultData.getCode()).as("添加数据,应该返回正常状态").isEqualTo(ResultData.CODE_SUCCESS);
            T testData = this.getDataById((String) data.get("id"));
            assertThat(testData).as("添加后，再次查询数据，应该能够获取数据").isNotNull();
            assertThat(testData.getId()).as("添加后，再次查询数据，查询的id和指定的id是一样的").isEqualTo(data.get("id"));

            // 修改，比较status前后的值
            String newStatus = StringUtil.fixNull((String) data.get("status")) + "_new";
            data.put("status", newStatus);
            ResponseEntity<ResultData> updateResponseEntity = restTemplate.postForEntity(this.getBasePath() + "/", data, ResultData.class);
            ResultData updateResultData = updateResponseEntity.getBody();
            assertThat(updateResultData).as("修改数据后，返回不为空").isNotNull();
            assertThat(updateResultData.getCode()).as("修改数据后,应该返回正常状态").isEqualTo(ResultData.CODE_SUCCESS);
            T afterUpdateData = this.getDataById((String) data.get("id"));
            assertThat(afterUpdateData).as("修改后，再次查询数据，应该能够获取数据").isNotNull();
            assertThat(afterUpdateData.getId()).as("修改后，再次查询数据，查询的id和指定的id应该是一样的").isEqualTo(data.get("id"));
            assertThat(afterUpdateData.getStatus()).as("修改后，再次查询数据，查询的状态，应该是新值").isEqualTo(newStatus);


        }

        // 单个删除，删除之后，找不到对应的数据
        restTemplate.delete(this.getBasePath() + "/{id}", this.getDataId());
        ResultData afterDeleteResultData = this.getResultById(this.getDataId());
        assertThat(afterDeleteResultData.getData()).as("删除之后，再次查询数据，应该获取不到数据").isNull();
        assertThat(afterDeleteResultData.getCode()).as("删除之后，再次查询数据，状态应该是404").isEqualTo(ResultData.CODE_NOT_FOUND);

        // 多个删除接口
        dDeleteByIds();
    }

    /**
     * 搜索接口验证
     * 调用搜索接口
     * 搜索到的数据不为空
     */
    @Test
    public void t401Search() {
        ResultData resultData = restTemplate.getForObject(this.getBasePath() + "/", ResultData.class);
        analyzeResultData(resultData, Page.class);
        Page page = (Page) resultData.getData();
        List data = page.getData();
        assertThat(data).as("搜索应该有返回结果").isNotNull();
    }

    /**
     * 验证 根据id参数，多个删除
     * 调用删除接口
     * 验证数据是否被删除
     */
    public void dDeleteByIds() {
        // 获取所有数据
        List<Map<String, Object>> dataList = this.getCreateData();

        // 获取子一个数据
        Map<String, Object> mainData = dataList.get(0);

        StringBuffer ids = new StringBuffer();
        for (Map<String, Object> data : dataList) {
            if (!data.equals(mainData)) {
                if (ids.length() > 0) {
                    ids.append(",");
                }
                ids.append(data.get("id"));
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);

        restTemplate.delete(this.getBasePath() + "/?ids={ids}", params);

        for (Map<String, Object> data : dataList) {
            if (!data.equals(mainData)) {
                ResultData resultData = this.getResultById((String) data.get("id"));
                assertThat(resultData.getData()).as("删除之后，再次查询数据，应该获取不到数据").isNull();
                assertThat(resultData.getCode()).as("删除之后，再次查询数据，状态应该是404").isEqualTo(ResultData.CODE_NOT_FOUND);
            }
        }
    }

    /**
     * 测试后，销毁数据
     */
    @Test
    public void t999Destroy() {
        this.destroy();
    }

    /**
     * 解析结果数据
     * 要将结果集里面的json数据，转换为真正的对象
     * @param resultData 结果集对象，里面存放的数据
     * @param clazz data数据的类型，一般是page
     */
    protected void analyzeResultData(ResultData resultData, Class clazz) {
        JSONObject jsonData = (JSONObject) resultData.getData();
        if (jsonData != null) {
            @SuppressWarnings("unchecked")
            Object data = jsonData.toJavaObject(clazz);
            resultData.setData(data);
        }
    }

    /**
     * 根据id获取结果
     * 调用http接口，根据地id获取数据
     * @param id 数据id
     * @return 结果对象
     */
    protected ResultData getResultById(String id) {
        ResultData resultData = restTemplate.getForObject(this.getBasePath() + "/{id}", ResultData.class, id);
        analyzeResultData(resultData, this.getDataType());
        return resultData;
    }

    /**
     * 根据id获取数据
     * 解析结果数据，获取ResultData中的data字段
     * @param id 数据id
     * @return 结果对象中的数据
     */
    @SuppressWarnings("unchecked")
    protected T getDataById(String id) {
        ResultData resultData = this.getResultById(id);
        if (ResultData.CODE_SUCCESS.equals(resultData.getCode())) {
            return (T) resultData.getData();
        } else {
            return null;
        }
    }

    /**
     * 获取基本的路径
     * @return 实体的路径
     */
    protected abstract String getBasePath();

    /**
     * 获取初始化的对象
     * @return 获取数据，用于创建对象
     */
    protected abstract List<Map<String, Object>> getCreateData();

    /**
     * 获取数据的id
     * @return 获取数据的id
     */
    protected abstract String getDataId();

    /**
     * 获取修改状态
     * @return 修改成的数据
     */
    protected abstract String getUpdateStatus();

    /**
     * 获取数据类型
     * @return 实体的类型
     */
    protected abstract Class<T> getDataType();

    /**
     * 准备数据
     * 比如验证实体，先要创建模块
     */
    protected abstract void ready();

    /**
     * 销毁数据
     * 准备的数据要被删除，不然会有很多脏数据
     */
    protected abstract void destroy();
}
