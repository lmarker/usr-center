package com.maksdu.usr.center.server.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksdu.usr.center.core.proxy.common.ErrorResult;
import com.maksdu.usr.center.core.proxy.common.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
@Component("RestAuthenticationAccessDeniedHandler")
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = new ObjectMapper()
                .writeValueAsString(ErrorResult.of(ResultCode.FORBIDDEN.getValue(), accessDeniedException.getMessage()));
        printWriter.write(body);
        printWriter.flush();
    }
}
