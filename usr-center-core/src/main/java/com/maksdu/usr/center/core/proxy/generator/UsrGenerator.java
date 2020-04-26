package com.maksdu.usr.center.core.proxy.generator;

import com.maksdu.usr.center.core.proxy.utils.AbstractGenerator;
import org.springframework.stereotype.Component;

@Component
public class UsrGenerator extends AbstractGenerator {
    @Override
    protected String prefix() {
        return "USR";
    }
}
