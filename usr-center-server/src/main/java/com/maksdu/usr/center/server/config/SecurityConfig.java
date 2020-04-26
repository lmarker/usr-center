package com.maksdu.usr.center.server.config;

import com.maksdu.usr.center.server.authentication.WeChatAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;


@Slf4j
@Configuration
public class SecurityConfig {

    @Configuration
    @AllArgsConstructor
    public static class CheckTokenSecurityConfig extends WebSecurityConfigurerAdapter {

        private final WeChatAuthenticationFilter weChatAuthenticationFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/wx/*","/auth/*")
                    .permitAll()
                    .anyRequest()
                    .authenticated();

            //无状态session;
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(weChatAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            http.cors().disable();

            http.csrf().disable();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .requestMatchers(CorsUtils::isPreFlightRequest)
                    .antMatchers("/v2/api-docs",
                            "/swagger-resources/configuration/ui",
                            "/swagger-resources",
                            "/swagger-resources/configuration/security",
                            "/swagger-ui.html");
        }
    }
}
