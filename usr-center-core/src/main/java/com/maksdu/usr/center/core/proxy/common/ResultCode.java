package com.maksdu.usr.center.core.proxy.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200),
    UNAUTHORIZED(401),

    USER_NOT_LOGGED_IN(20001),

    ERROR_THROW(50001);

    Integer value;

}
