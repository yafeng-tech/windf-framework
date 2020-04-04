package com.windf.plugin.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.windf.core.entity.BaseEntity;
import com.windf.core.entity.Page;
import com.windf.core.entity.ResultData;
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

    @Test
    public void t000Ready() {
        // 准备其他数据
        this.ready();
    }


    @Test
    public void t101Create() {
        // 获取预备数据
        List<T> dataList = this.getCreateData();

        // 依次添加
        for (T data : dataList) {
            ResponseEntity<ResultData> responseEntity = restTemplate.postForEntity(this.getBasePath() + "/", data, ResultData.class);
            ResultData resultData = responseEntity.getBody();
            Assert.assertEquals(ResultData.CODE_SUCCESS, resultData.getCode());

            T testData = this.getDataById(data.getId());
            Assert.assertNotNull(testData); // TODO 设置断言的消息
            Assert.assertEquals(testData.getId(), data.getId());
        }
    }

    @Test
    public void t201Detail() {
        T data = this.getDataById(this.getDataId());
        Assert.assertEquals(this.getDataId(), data.getId());
    }

    @Test
    public void t301Update() {
        T mainData = this.getCreateData().get(0);

        // 修改之前
        T beforeUpdateData = this.getDataById(this.getDataId());
        Assert.assertNotEquals(this.getUpdateStatus(), beforeUpdateData.getStatus());

        // 修改，修改名称
        mainData.setStatus(this.getUpdateStatus());
        ResponseEntity<ResultData> responseEntity = restTemplate.postForEntity(this.getBasePath() + "/", mainData, ResultData.class);
        ResultData resultData = responseEntity.getBody();
        Assert.assertEquals(ResultData.CODE_SUCCESS, resultData.getCode());

        // 修改之后
        T afterUpdateData = this.getDataById(this.getDataId());
        Assert.assertEquals(this.getUpdateStatus(), afterUpdateData.getStatus());
    }

    @Test
    public void t401Search() {
        ResultData resultData = restTemplate.getForObject(this.getBasePath() + "/", ResultData.class);
        analyzeResultData(resultData, Page.class);
        Page page = (Page) resultData.getData();
        List<T> data = page.getData();
        Assert.assertNotNull(data);
    }

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

    @Test
    public void t601DeleteByIds() {

        // 获取所有数据
        List<T> dataList = this.getCreateData();

        // 获取子一个数据
        T mainData = dataList.get(0);

        String ids = "";
        for (T data : dataList) {
            if (!data.equals(mainData)) {
                ids += "," + data.getId();
            }
        }
        if (ids.startsWith(",")) {
            ids = ids.substring(1);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);

        restTemplate.delete(this.getBasePath() + "/?ids={ids}", params);

        for (T data : dataList) {
            if (!data.equals(mainData)) {
                ResultData resultData = this.getResultById(data.getId());
                Assert.assertEquals(ResultData.CODE_NOT_FOUND, resultData.getCode());
            }
        }
    }

    /**
     * 销毁数据
     */
    @Test
    public void t999Destroy() {
        this.destroy();
    }

    /**
     * 解析结果数据
     * @param resultData
     * @param clazz
     * @return
     */
    protected ResultData analyzeResultData(ResultData resultData, Class clazz) {
        JSONObject jsonData = (JSONObject) resultData.getData();
        if (jsonData != null) {
            Object data = jsonData.toJavaObject(clazz);
            resultData.setData(data);
        }

        return resultData;
    }

    /**
     * 根据id获取结果
     * @param id
     * @return
     */
    protected ResultData getResultById(String id) {
        ResultData resultData = restTemplate.getForObject(this.getBasePath() + "/{id}", ResultData.class, id);
        resultData = analyzeResultData(resultData, this.getDataType());
        return resultData;
    }

    /**
     * 根据id获取数据
     * @param id
     * @return
     */
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
     * @return
     */
    protected abstract String getBasePath();

    /**
     * 获取初始化的对象
     * @return
     */
    protected abstract List<T> getCreateData();

    /**
     * 获取数据的id
     * @return
     */
    protected abstract String getDataId();

    /**
     * 获取修改状态
     * @return
     */
    protected abstract String getUpdateStatus();

    /**
     * 获取数据类型
     * @return
     */
    protected abstract Class<T> getDataType();

    /**
     * 准备数据
     */
    protected abstract void ready();

    /**
     * 销毁数据
     */
    protected abstract void destroy();
}
