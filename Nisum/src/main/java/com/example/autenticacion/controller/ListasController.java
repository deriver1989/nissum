package com.example.autenticacion.controller;


import com.example.autenticacion.response.ResponseDTO;
import com.example.autenticacion.service.MunicipioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListasController {


    @Autowired
    private MunicipioServiceImpl municipioService;



    @GetMapping("/consultar-municipios")
    public ResponseDTO consultarMunicipios(){
        try {
            // Respuesta estándar en caso de éxito
            ResponseDTO response = new ResponseDTO(200, "Consulta OK", municipioService.listaMunicipio());
            return response;

        } catch (Exception e) {
            // Respuesta estándar en caso de error
            ResponseDTO response = new ResponseDTO(404, "Error al consultar los municipios", null);
            return response;
        }
    }

}
