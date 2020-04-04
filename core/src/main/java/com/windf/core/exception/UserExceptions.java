package com.windf.core.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户异常集合
 * 多个用户异常结合在一起，统一抛出
 */
public class UserExceptions extends UserException {

    private List<UserException> userExceptionList = new ArrayList<UserException>();

    public void addException(UserException userException) {
        userExceptionList.add(userException);
    }

    public List<UserException> getUserExceptionList() {
        return userExceptionList;
    }

    public boolean hasException() {
        return userExceptionList.size() > 0;
    }

}
