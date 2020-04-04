package com.windf.plugin.controller.api.advice;

import com.windf.core.entity.ResultData;
import com.windf.core.exception.ParameterException;
import com.windf.core.exception.UnsupportException;
import com.windf.core.exception.UserException;
import com.windf.core.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorControllerAdvice {
    /**
     * 通用用户异常的错误返回
     * @param e
     * @return
     */
    @ExceptionHandler(value = {UserException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResultData handleUserException(UserException e) {
        ResultData resultData = new ResultData();
        // 设置错误编号，如果没有异常编号，设置为500
        resultData.setCode(StringUtil.fixNull(e.getType(), ResultData.CODE_NORMAL_ERROR));
        // 设置异常信息
        resultData.setMessage(e.getMessage());
        return resultData;
    }

    /**
     * 参数错误
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData handleParameterException(ParameterException e) {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultData.CODE_BAD_REQUEST);
        resultData.setMessage("错误的请求");
        return resultData;
    }

    /**
     * 通用异常返回
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData handleNormalException(Throwable e) {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultData.CODE_NORMAL_ERROR);
        resultData.setMessage("发生了未知错误");
        e.printStackTrace();
        return resultData;
    }

    /**
     * 拦截UnsupportException，不支持的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {UnsupportException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultData handleUnsupportException(Throwable e) {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultData.CODE_NOT_FOUND);
        resultData.setMessage("资源不存在");
        return resultData;
    }

}
