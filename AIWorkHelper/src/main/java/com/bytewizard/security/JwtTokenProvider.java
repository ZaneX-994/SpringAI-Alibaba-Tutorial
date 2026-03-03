package com.bytewizard.security;

import com.bytewizard.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token提供者
 * 负责Token的生成和验证
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    /**
     * 用户标识键名
     */
    public static final String IDENTIFY = "aiworkhelper";

    /**
     * Authorization头名称
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * Bearer前缀
     */
    public static final String BEARER_PREFIX = "Bearer ";


    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        // 使用配置的密钥生成SecretKey
        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * 生成JWT Token
     */
    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpire() * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put(IDENTIFY, userId);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)  // issue at - 签发时间
                .expiration(expiryDate)  // expiration - 过期时间
                .signWith(secretKey, Jwts.SIG.HS256)  // 使用HS256算法签名
                .compact();
    }

    /**
     * 获取Token过期时间戳
     *
     * @return 过期时间戳（秒）
     */
    public Long getExpirationTime() {
        return System.currentTimeMillis() / 1000 + jwtProperties.getExpire();
    }

}
