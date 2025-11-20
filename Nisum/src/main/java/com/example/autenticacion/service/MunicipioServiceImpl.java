package com.example.autenticacion.service;

import com.example.autenticacion.entities.MunicipioEntity;
import com.example.autenticacion.reporitories.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MunicipioServiceImpl {

    @Autowired
    private MunicipioRepository municipioRepository;


    @Cacheable("municipios")
    public Iterable<MunicipioEntity> listaMunicipio (){
        return municipioRepository.findAll(Sort.by("nombre"));
    }

}
