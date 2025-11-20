package com.example.autenticacion.reporitories;

import com.example.autenticacion.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail (String username);

}
