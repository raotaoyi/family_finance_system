package com.family.finance.system.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire}")
    private Long expire;

    // 生成token
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 解析token获取用户id
    public Long getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getSubject());
    }
}
