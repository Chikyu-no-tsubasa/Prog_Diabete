package it.unibs.diabetici.bootstrap;

import it.unibs.diabetici.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
  private final UtenteRepository utenti; 
  private final PazienteRepository pazienti; 
  private final DiabetologoRepository medici;

  @Override public void run(String... args){
    if(utenti.count()>0) return;
    var enc = new BCryptPasswordEncoder();

    var m1 = medici.save(Diabetologo.builder().nome("Dario").cognome("Bianchi").email("d.bianchi@svc.it").build());
    var m2 = medici.save(Diabetologo.builder().nome("Elena").cognome("Rossi").email("e.rossi@svc.it").build());

    var p1 = pazienti.save(Paziente.builder().nome("Paolo").cognome("Verdi").email("p.verdi@svc.it").medicoRiferimento(m1).build());
    var p2 = pazienti.save(Paziente.builder().nome("Marta").cognome("Neri").email("m.neri@svc.it").medicoRiferimento(m2).build());

    utenti.save(Utente.builder().email(m1.getEmail()).passwordHash(enc.encode("medico")).role(Role.DIABETOLOGO).diabetologo(m1).build());
    utenti.save(Utente.builder().email(m2.getEmail()).passwordHash(enc.encode("medico")).role(Role.DIABETOLOGO).diabetologo(m2).build());
    utenti.save(Utente.builder().email(p1.getEmail()).passwordHash(enc.encode("paziente")).role(Role.PAZIENTE).paziente(p1).build());
    utenti.save(Utente.builder().email(p2.getEmail()).passwordHash(enc.encode("paziente")).role(Role.PAZIENTE).paziente(p2).build());
  }
}
