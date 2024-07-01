package com.iishanto.kikhabo.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private String jwtSecret="shanto2023";
    private long jwtExpiration=3123123123l;

    public String generateToken(Authentication authentication){
        String username=authentication.getName();
        Date today=new Date();
        Date expireDate=new Date(today.getTime()+jwtExpiration);
        SecretKey secretKey= Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().
                setSubject(username).
                setIssuer("JWT Service").
                setIssuedAt(today).
                setExpiration(expireDate).
                signWith(secretKey,SignatureAlgorithm.ES256)
                .compact();
    }
}
