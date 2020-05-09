package com.maksdu.usr.center.core.proxy.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lijiahao
 * @since 2020/5/9
 */
@Data
@AllArgsConstructor
public class ErrorResult {

    private Integer code;

    private String errorMsg;

    public static ErrorResult of(Integer code, String errorMsg) {
        return new ErrorResult(code, errorMsg);
    }
}
