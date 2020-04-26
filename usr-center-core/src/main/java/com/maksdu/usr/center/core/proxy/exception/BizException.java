package com.maksdu.usr.center.core.proxy.exception;

import com.maksdu.usr.center.core.proxy.common.ResultCode;

public class BizException extends RuntimeException {

    private ResultCode code;
    private String message;

    public BizException(ResultCode code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCode.ERROR_THROW;
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = ResultCode.ERROR_THROW;
    }

    protected BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = ResultCode.ERROR_THROW;
    }

    public static BizException build(ResultCode code, String message) {
        return new BizException(code, message);
    }

    public static BizException build(String message) {
        return new BizException(ResultCode.SUCCESS, message);
    }
}
