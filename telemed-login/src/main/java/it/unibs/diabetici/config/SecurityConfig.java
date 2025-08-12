package it.unibs.diabetici.config;

import it.unibs.diabetici.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthFilter jwtAuthFilter;
  private final UserDetailsService uds;

  @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

  @Bean AuthenticationManager authenticationManager(){
    DaoAuthenticationProvider p = new DaoAuthenticationProvider();
    p.setPasswordEncoder(passwordEncoder());
    p.setUserDetailsService(uds);
    return new ProviderManager(p);
  }

  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable())
       .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       .authorizeHttpRequests(a->a
          .requestMatchers("/api/auth/login").permitAll()
          .anyRequest().authenticated())
       .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
