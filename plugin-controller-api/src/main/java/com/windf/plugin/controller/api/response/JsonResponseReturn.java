package com.windf.plugin.controller.api.response;

import com.windf.core.entity.ResultData;
import com.windf.core.util.StringUtil;

public class JsonResponseReturn implements ResponseReturn {

    @Override
    public ResultData parameterError() {
        return null;
    }

    @Override
    public ResultData success() {
        return returnData(ResultData.CODE_SUCCESS, ResultData.MESSAGE_SUCCESS, null);
    }

    @Override
    public ResultData success(String message) {
        return returnData(ResultData.CODE_SUCCESS, message, null);
    }

    @Override
    public ResultData successData(Object data) {
        return returnData(ResultData.CODE_SUCCESS, ResultData.MESSAGE_SUCCESS, data);
    }

    @Override
    public ResultData error(String message) {
        return error(ResultData.CODE_NORMAL_ERROR, message);
    }

    @Override
    public ResultData error(String code, String message) {
        return returnData(code, message, null);
    }

    @Override
    public ResultData returnData(String code, String message, Object data) {
        ResultData resultData = new ResultData();

        // 设置返回的状态
        resultData.setCode(code);

        // 设置返回的数据
        if (data != null) {
            resultData.setData(data);
        }

        // 设置返回的消息
        if (StringUtil.isNotEmpty(message)) {
            resultData.setMessage(message);
        }

        return resultData;
    }

    @Override
    public ResultData redirect(String url) {
        return returnData(ResultData.CODE_REDIRECT ,null, url);
    }

    @Override
    public ResultData redirectData(String url, Object data) {
        return successData(url);
    }
}
