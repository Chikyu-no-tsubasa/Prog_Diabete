package it.unibs.diabetici.security;

import it.unibs.diabetici.auth.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UtenteRepository repo;
  @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var u = repo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("not found"));
    return User.withUsername(u.getEmail()).password(u.getPasswordHash()).roles(u.getRole().name()).build();
  }
}
