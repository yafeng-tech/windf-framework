package cn.windf.plugin.controller.api;

import com.alibaba.fastjson.JSONObject;
import cn.windf.core.entity.BaseEntity;
import cn.windf.core.entity.Page;
import cn.windf.core.entity.ResultData;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseManageControllerTest<T extends BaseEntity> {

    @Autowired
    protected TestRestTemplate restTemplate;

    /**
     * 准备数据
     */
    @Test
    public void t000Ready() {
        this.ready();
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
            ResponseEntity<ResultData> responseEntity = restTemplate.postForEntity(this.getBasePath() + "/", data, ResultData.class);
            ResultData resultData = responseEntity.getBody();
            Assert.assertNotNull(resultData);
            Assert.assertEquals(ResultData.CODE_SUCCESS, resultData.getCode());

            T testData = this.getDataById((String) data.get("id"));
            Assert.assertNotNull(testData); // TODO 设置断言的消息
            Assert.assertEquals(testData.getId(), data.get("id"));
        }
    }

    /**
     * 验证单个数据查询
     * 获取数据，id不为空
     * 很多接口都依赖于这个接口验证，如果这个失败了，基本上每个接口都会失败
     */
    @Test
    public void t201Detail() {
        T data = this.getDataById(this.getDataId());
        Assert.assertEquals(this.getDataId(), data.getId());
    }

    /**
     * 调用修改接口
     * 先验证修改之前的状态
     * 修改状态为一个不同的值，
     * 然后进行判断时候修改成功
     */
    @Test
    public void t301Update() {
        Map<String, Object> mainData = this.getCreateData().get(0);

        // 修改之前
        T beforeUpdateData = this.getDataById(this.getDataId());
        Assert.assertNotEquals(this.getUpdateStatus(), beforeUpdateData.getStatus());

        // 修改，修改名称
        mainData.put("status", this.getUpdateStatus());
        ResponseEntity<ResultData> responseEntity = restTemplate.postForEntity(this.getBasePath() + "/", mainData, ResultData.class);
        ResultData resultData = responseEntity.getBody();
        Assert.assertNotNull(resultData);
        Assert.assertEquals(ResultData.CODE_SUCCESS, resultData.getCode());

        // 修改之后
        T afterUpdateData = this.getDataById(this.getDataId());
        Assert.assertEquals(this.getUpdateStatus(), afterUpdateData.getStatus());
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
        Assert.assertNotNull(data);
    }

    /**
     * 验证url参数删除
     * 先验证对象存在，
     * 调用删除接口
     * 最后验证数据是否被删除
     */
    @Test
    public void t501Delete() {
        // 删除之前
        T beforeUpdateResultData = this.getDataById(this.getDataId());
        Assert.assertNotNull(beforeUpdateResultData);

        // 删除
        restTemplate.delete(this.getBasePath() + "/{id}", this.getDataId());

        // 删除之后
        ResultData afterDeleteResultData = this.getResultById(this.getDataId());
        Assert.assertEquals(ResultData.CODE_NOT_FOUND, afterDeleteResultData.getCode());
    }

    /**
     * 验证 根据id参数，多个删除
     * 调用删除接口
     * 验证数据是否被删除
     */
    @Test
    public void t601DeleteByIds() {
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
                Assert.assertEquals(ResultData.CODE_NOT_FOUND, resultData.getCode());
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
