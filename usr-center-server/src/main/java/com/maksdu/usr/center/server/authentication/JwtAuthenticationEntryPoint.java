package com.maksdu.usr.center.server.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksdu.usr.center.core.proxy.common.Result;
import com.maksdu.usr.center.core.proxy.common.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lijiahao
 * @since 2020/5/9
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.
                writeValueAsString(Result.of(ResultCode.UNAUTHORIZED.getValue(), authException.getMessage(), authException.getMessage()));
        printWriter.write(body);
        printWriter.flush();
    }
}
