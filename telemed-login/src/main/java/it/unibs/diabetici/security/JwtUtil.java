package it.unibs.diabetici.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key; import java.util.*;

@Component
public class JwtUtil {
  private final Key key; private final long exp;
  public JwtUtil(@Value("${security.jwt.secret}") String secret, @Value("${security.jwt.expiration}") long exp){
    this.key = Keys.hmacShaKeyFor(secret.getBytes()); this.exp = exp;
  }
  public String generate(String sub, Map<String,Object> claims){
    Date now = new Date();
    return Jwts.builder().setSubject(sub).addClaims(claims).setIssuedAt(now)
      .setExpiration(new Date(now.getTime()+exp)).signWith(key, SignatureAlgorithm.HS256).compact();
  }
  public Jws<Claims> parse(String token){
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
  }
}
