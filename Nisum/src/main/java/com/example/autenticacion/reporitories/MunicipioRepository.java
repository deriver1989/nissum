package com.example.autenticacion.reporitories;

import com.example.autenticacion.entities.MunicipioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipioRepository extends JpaRepository<MunicipioEntity, Long> {

    Optional<MunicipioEntity> findByNombre (String username);

}
