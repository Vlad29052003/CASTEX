package com.example.demo.services;

import com.example.demo.entities.JwtToken;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.JwtTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String KEY = "3762533764314C4257736361674C6641726469673146454E6971736B48665A4346614C645142796C6A7944456D6A2B414C375A674D43717367426E7A4B4D4B620D0A";
    private final JwtTokenRepository tokenRepository;

    public JwtService(JwtTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String extractEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(Map.of())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 15)) //15 days
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 15)) //15 days
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken saveToken(String token, UserEntity user) {
        JwtToken jwtToken = new JwtToken(token, user);
        return tokenRepository.saveAndFlush(jwtToken);
    }

    public JwtToken find(String token) {
        return tokenRepository.findById(token).orElse(null);
    }

    public void deleteToken(String token) {
        if (tokenRepository.existsById(token))
            tokenRepository.deleteById(token);
    }

    public List<JwtToken> findAllValidTokenByUser(String email) {
        return tokenRepository.findAllValidTokenByUser(email);
    }

    public List<JwtToken> saveAll(List<JwtToken> validUserTokens) {
        return tokenRepository.saveAllAndFlush(validUserTokens);
    }
}
