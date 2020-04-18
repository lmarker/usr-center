package com.maksdu.usr.center.core.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author lijiahao
 */
@Configuration
@Slf4j
public class FeignApiConfig {

    private ErrorDecoder decode = new ErrorDecoder.Default();
    private Decoder decoder = new Decoder.Default();


    ErrorDecoder _400errorDecoder(ObjectMapper objectMapper) {
        return (methodKey, response) -> {
            if(response.status() == HttpStatus.SC_BAD_REQUEST) {
                try {
                    ErrorBody errorBody = objectMapper.readValue(response.body().asInputStream(), ErrorBody.class);
                    throw new FeignException.BadRequest(errorBody.getMessage(), response.request(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response.status() == 40164) {
                try {
                    WeChatErrorBody errorBody = objectMapper.readValue(response.body().asInputStream(), WeChatErrorBody.class);
                    throw new FeignException.BadRequest(errorBody.getErrmsg(), response.request(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return decode.decode(methodKey, response);
        };
    }

    @Bean
    Decoder weChatHttpMessageDecoder(ObjectMapper objectMapper) {
        return (response, type) -> {
            if(type.getTypeName().startsWith("com.maksdu.usr.center.core.proxy.dto")) {
                try {
                    return objectMapper.readValue(response.body().asInputStream(),
                            ClassUtils.getClass(type.getTypeName()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return decoder.decode(response, type);
        };
    }

    @Data
    private static class ErrorBody {
        private String message;
    }

    @Data
    private static class WeChatErrorBody {

        private String errcode;

        private String errmsg;
    }
}
