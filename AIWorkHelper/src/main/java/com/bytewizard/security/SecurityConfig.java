package com.bytewizard.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Security配置
 * 配置JWT认证、CORS、授权规则等
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 禁用 CSRF
                .csrf(csrf -> csrf.disable())

                // 2. 配置跨域
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 3. 配置Session管理为无状态（使用JWT不需要Session）
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. 配置授权规则
                .authorizeHttpRequests(auth -> auth
                        // 登录接口允许匿名访问
                        .requestMatchers("/v1/user/login").permitAll()

                        // WebSocket连接允许访问（在WebSocket层面进行Token验证）
                        .requestMatchers("/ws/**").permitAll()

                        // 健康检查接口允许访问
                        .requestMatchers("/actuator/**").permitAll()

                        // 知识库诊断接口允许访问
                        .requestMatchers("/api/knowledge/diag/**").permitAll()

                        // 其他所有请求都需要认证
                        .anyRequest().authenticated()
                )

                // 4. 配置会话管理
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 5. 添加JWT认证过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORS配置
     * 允许前端跨域访问
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 允许所有来源（生产环境应该限制具体域名）
        configuration.setAllowedOriginPatterns(List.of("*"));

        // 允许的HTTP方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 允许的请求头
        configuration.setAllowedHeaders(List.of("*"));

        // 允许携带认证信息（Cookie、Authorization头等）
        configuration.setAllowCredentials(true);

        // 暴露的响应头
        configuration.setExposedHeaders(Arrays.asList("Authorization", "websocket"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
