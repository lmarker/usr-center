package com.maksdu.usr.center.core.proxy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface GenderEnum {

    @Getter
    @AllArgsConstructor
    enum Gender {
        BOY(1, "男"),
        GIRL(0, "女")
        ;
        private Integer value;

        private String desc;
    }
}
