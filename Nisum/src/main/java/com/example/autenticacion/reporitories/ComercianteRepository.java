package com.example.autenticacion.reporitories;

import com.example.autenticacion.entities.ComercianteEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComercianteRepository extends CrudRepository<ComercianteEntity, Long> ,
        JpaSpecificationExecutor<ComercianteEntity> {

}
