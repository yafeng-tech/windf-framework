package com.windf.plugin.controller.api.response;

import com.windf.core.entity.ResultData;

public interface ResponseReturn {

    /**
     * 返回错误信息
     *
     * @return
     */
    ResultData parameterError();

    /**
     * 返回成功提示
     *
     * @return
     */
    ResultData success();

    /**
     * 返回成功提示
     * 默认的code为 {@link ResultData#CODE_SUCCESS}
     * 默认的data为 null
     *
     * @return
     */
    ResultData success(String message);

    /**
     * 返回带数据的成功信息
     * 默认的code为 {@link ResultData#CODE_SUCCESS}
     * 默认的message为code {@link ResultData#MESSAGE_SUCCESS}
     *
     * @param data
     * @return
     */
    ResultData successData(Object data);

    /**
     * 返回通用的错误信息
     * 默认的code为 {@link ResultData#CODE_NORMAL_ERROR}
     * 默认的data为 null
     *
     * @param message
     * @return
     */
    ResultData error(String message);

    /**
     * 返回指定code的错误信息
     * 默认的data为 null
     *
     * @param code
     * @param message
     * @return
     */
    ResultData error(String code, String message);

    /**
     * 返回带数据和提示的信息
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    ResultData returnData(String code, String message, Object data);

    /**
     * 重定向到某个页面
     * 默认的code为 {@link ResultData#CODE_REDIRECT}
     *
     * @param url
     * @return
     */
    ResultData redirect(String url);

    /**
     * 重定向到某个页面,携带参数
     * 默认的code为 {@link ResultData#CODE_REDIRECT}
     *
     * @param url
     * @param data
     * @return
     */
    ResultData redirectData(String url, Object data);
}
