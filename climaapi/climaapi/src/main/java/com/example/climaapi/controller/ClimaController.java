package com.example.climaapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.climaapi.service.ClimaService;

@RestController
public class ClimaController {

    private final ClimaService climaService;

    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }

    // https://localhost:8080/climaBH
    @GetMapping("/climaBH")
    public String buscarClimaBH(){
        return climaService.buscarClimaBH();
    }

    // https://localhost:8080/clima/curitiba
    @GetMapping("/clima/{cidade}")
    public String buscarClima(@PathVariable String cidade){
        return climaService.buscarClima(cidade);
    }
}