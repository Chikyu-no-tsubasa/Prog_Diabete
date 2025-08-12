package it.unibs.diabetici.security;

import jakarta.servlet.*; import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component @RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtil jwt; private final UserDetailsService uds;
  @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
    throws java.io.IOException, ServletException {
    String h = req.getHeader("Authorization");
    if(h!=null && h.startsWith("Bearer ")){
      try{
        var claims = jwt.parse(h.substring(7)).getBody();
        var ud = uds.loadUserByUsername(claims.getSubject());
        var auth = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
      }catch(Exception ignored){}
    }
    chain.doFilter(req,res);
  }
}
