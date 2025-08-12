package it.unibs.diabetici.auth;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="utenti")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Utente {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique = true)
  private String email;

  @Column(nullable=false)
  private String passwordHash;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private Role role;

  @OneToOne(fetch = FetchType.LAZY)
  private Paziente paziente;

  @OneToOne(fetch = FetchType.LAZY)
  private Diabetologo diabetologo;
}
