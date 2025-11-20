package com.example.autenticacion.response;

import com.example.autenticacion.entities.Phones;
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
public class UserResponse {

  private Integer id;
  private String name;
  private String email;
  private String password;
  private String rol;
  private List<Phones> phones;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime last_login;
  private Boolean isactive;
}
