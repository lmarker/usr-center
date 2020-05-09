package com.maksdu.usr.center.server.config;

import com.maksdu.usr.center.server.authentication.CustomUserDetailsService;
import com.maksdu.usr.center.server.authentication.JwtAuthenticationEntryPoint;
import com.maksdu.usr.center.server.authentication.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Slf4j
@Configuration
public class SecurityConfig {

    @Configuration
    @AllArgsConstructor
    public static class CheckTokenSecurityConfig extends WebSecurityConfigurerAdapter {

        private final JwtAuthenticationEntryPoint unauthorizedHandler;

        private final AccessDeniedHandler accessDeniedHandler;


        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        private final CustomUserDetailsService customUserDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                    .authorizeRequests()
                        .antMatchers("/wx/login","/error/**","/api/**").permitAll()
                    .anyRequest()
                    .authenticated();

            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            //无状态session;
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            //http.addFilterBefore(weChatAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            http.cors().disable();

            http.csrf().disable();
        }

        @Autowired
        public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
            authenticationManagerBuilder
                    // 设置UserDetailsService
                    .userDetailsService(this.customUserDetailsService)
                    // 使用BCrypt进行密码的hash
                    .passwordEncoder(passwordEncoder());
        }

        // 装载BCrypt密码编码器
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .requestMatchers(CorsUtils::isPreFlightRequest)
                    .antMatchers("/v2/api-docs",
                            "/swagger-resources/configuration/ui",
                            "/swagger-resources",
                            "/swagger-resources/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**");
        }
    }

    @Configuration
    public static class WebMvcConfigurer extends WebMvcConfigurationSupport {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**").addResourceLocations(
                    "classpath:/static/");
            registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                    "classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
            super.addResourceHandlers(registry);
        }

    }
}
