package com.example.autenticacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, name = "NOMBRE")
  private String name;

  @Column(name = "CORREO_ELECTRONICO" ,nullable = false, unique = true)
  private String email;

  @Column(name = "PASS", nullable = false)
  private String password;

  @Column( name = "ROL")
  private String rol;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Phones> phones;

  @Column( name = "created", updatable = false)
  private LocalDateTime created;

  @Column( name = "modified")
  private LocalDateTime modified;

  @Column( name = "last_login")
  private LocalDateTime last_login;

  @Column( name = "isactive")
  private Boolean isactive;
}
