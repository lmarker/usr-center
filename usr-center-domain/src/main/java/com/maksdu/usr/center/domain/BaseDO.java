package com.maksdu.usr.center.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
public abstract class BaseDO {

    private Long id;

    /**
     * 本地码
     */
    private String code;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
