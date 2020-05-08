package com.maksdu.usr.center.core.proxy.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IdContextGeneratorHolder implements IdGenerator, StringContextGenerator {

    private static ConcurrentMap<String, AtomicInteger> counter;
    private static ConcurrentMap<String, StringContextGenerator> holderMap;
    private IdGenerator generator;

    static {
        counter = new ConcurrentHashMap<>();
        holderMap = new ConcurrentHashMap<>();
    }

    private IdContextGeneratorHolder(IdGenerator generator) {
        this.generator = generator;
    }

    @Override
    public String prefix() {
        return generator.prefix();
    }

    @Override
    public String generate() {
        return prefix() + stamp() + count(this.generator);
    }

    public static String generatorId(IdGenerator generator) {
        String idKey = getKey(generator);
        StringContextGenerator contextGenerator = holderMap.getOrDefault(idKey, null);
        if(contextGenerator == null) {
            holderMap.putIfAbsent(idKey, new IdContextGeneratorHolder(generator));
            contextGenerator = holderMap.get(idKey);
        }
        return contextGenerator.generate();
    }

    private static String stamp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
                .replaceAll("-","")
                .replaceAll(":","");
    }

    private static String count(IdGenerator generator) {
        String key = getKey(generator);
        AtomicInteger number = counter.getOrDefault(key, null);
        if(number == null) {
            counter.putIfAbsent(key, new AtomicInteger(0));
            number = counter.get(key);
        }
        return String.format("%04d", number.incrementAndGet());
    }

    private static String getKey(IdGenerator generator) {
        return "usr:"+stamp()+":"+generator.prefix();
    }

}
