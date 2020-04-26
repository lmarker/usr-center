package com.maksdu.usr.center.core.proxy.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private Integer code;

    private Object result;

    private String errorMsg;

    public static Result of(Object result) {
        return new Result(200, result, "OK");
    }

    public static Result of(Integer code, Object result, String errorMsg) {
        return new Result(code, result, errorMsg);
    }
}
