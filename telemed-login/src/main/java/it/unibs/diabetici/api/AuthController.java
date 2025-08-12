package it.unibs.diabetici.api;

import it.unibs.diabetici.auth.*;
import it.unibs.diabetici.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor @CrossOrigin
public class AuthController {
  private final AuthenticationManager am; private final UtenteRepository utenti; private final JwtUtil jwt;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req){
    am.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
    var u = utenti.findByEmail(req.email()).orElseThrow();
    Map<String,Object> claims = new HashMap<>();
    claims.put("role", u.getRole().name());
    if(u.getPaziente()!=null){ claims.put("pazienteId", u.getPaziente().getId()); claims.put("medicoId", u.getPaziente().getMedicoRiferimento().getId()); }
    if(u.getDiabetologo()!=null){ claims.put("diabetologoId", u.getDiabetologo().getId()); }
    String token = jwt.generate(u.getEmail(), claims);
    return ResponseEntity.ok(new LoginResponse(token, u.getRole().name(),
      u.getPaziente()!=null?u.getPaziente().getId():null,
      u.getDiabetologo()!=null?u.getDiabetologo().getId():null));
  }
}
