package com.example.jwt.security.jwt;

import com.example.jwt.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvide {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvide.class);
    private String jwtSecret = "kiet.ht@gmail.com";
    private int jwtExpiration = 86400;

    public String createToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parsePlaintextJws(token);
            return true;
        } catch (SignatureException e){
            logger.error("valid JWT signature -> Message: {}", e);
        } catch (MalformedJwtException e){
            logger.error("The token invalid format -> Message: {}");
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT token ->Message: {}",e);
        } catch (ExpiredJwtException e){
            logger.error("Expried JWT Token ->Message: {}", e);
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty ->Message:{}",e);
        }
        return false;
    }

    public String getUserNameFromToken(String token){
        String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }











}
