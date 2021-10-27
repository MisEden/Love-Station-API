package org.eden.lovestation.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.eden.lovestation.exception.business.InvalidJWTException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JWTUtil {

    private String secretKey;
    private int lifeTime;

    public String sign(String userId, String role) {
        Claims claims = Jwts.claims();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, lifeTime);
        claims.setExpiration(calendar.getTime());
        claims.put("userId", userId);
        claims.put("role", role);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder().setClaims(claims).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public Payload verify(String token) throws InvalidJWTException {
        String userId;
        String role;
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            userId = claims.get("userId").toString();
            role = claims.get("role").toString();
        } catch (Exception e) {
            throw new InvalidJWTException();
        }
        return new Payload(userId, role);
    }
}
