package wtc.mall.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    // jwt有一天的时间
    private static final long EXPIRATION_TIME = 1000*60*60*24;
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken (String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role",role) // 往token里面存角色。用于interceptor验证
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims paresToken (String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
