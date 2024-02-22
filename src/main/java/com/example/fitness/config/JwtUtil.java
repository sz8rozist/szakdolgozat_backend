package com.example.fitness.config;

import com.example.fitness.model.User;
import com.example.fitness.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.catalina.Globals;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private long accessTokenValidity = 60*60*1000;

    private SecretKey secretKey;

    private final JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private final UserRepository userRepository;

    public JwtUtil(UserRepository userRepository){
        this.userRepository = userRepository;
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.jwtParser = Jwts.parser().setSigningKey(this.secretKey);
    }
    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("username",username);
        Optional<User> user = userRepository.findByUsername(username);
        claims.put("role", user.get().getRoles());
        claims.setSubject(String.valueOf(user.get().getId()));
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();
    }

    Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

}
