package it.unibs.diabetici.auth;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Diabetologo {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable=false) private String nome;
  @Column(nullable=false) private String cognome;
  @Column(nullable=false, unique = true) private String email;
}
