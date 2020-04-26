package com.maksdu.usr.center.core.proxy.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractGenerator {

    private static Map<String, Integer> counter = new ConcurrentHashMap<>();

    protected abstract String prefix();

    private String stamp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
                .replaceAll("-","")
                .replaceAll(":","");
    }

    private String count() {
        Integer number = counter.getOrDefault(getKey(), 0);
        counter.put(getKey(), ++number);
        return String.format("%4d",number);
    }

    private String getKey() {
        return "usr:"+stamp()+":"+prefix();
    }

    public String getId() {
        return prefix() + stamp() + count();
    }

}
