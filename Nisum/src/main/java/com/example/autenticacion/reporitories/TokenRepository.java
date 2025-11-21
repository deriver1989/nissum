package com.example.autenticacion.reporitories;

import com.example.autenticacion.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


  @Query(value = """
      select t from Token t inner join User u \s
      on t.user.id = u.id \s
      where u.id = ?1 and (t.isExpired = false or t.isRevoked = false)
      """)

  List<Token> findAllValidTokenByUser(Integer id);

  Optional<Token> findByToken(String token);
}
