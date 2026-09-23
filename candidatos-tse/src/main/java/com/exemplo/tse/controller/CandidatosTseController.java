package com.exemplo.tse.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exemplo.tse.model.Candidato;
import com.exemplo.tse.service.CandidatosTseService;

@Controller
public class CandidatosTseController {

    private final CandidatosTseService candidatosTseService;

    public CandidatosTseController(CandidatosTseService candidatosTseService) {
        this.candidatosTseService = candidatosTseService;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) String partido,
            @RequestParam(required = false) String texto,
            Model model) {

        String cargoFiltro = (cargo == null) ? "" : cargo;
        String partidoFiltro = (partido == null) ? "" : partido;
        String textoFiltro = (texto == null) ? "" : texto;

        List<Candidato> candidatos = candidatosTseService.filtrar(cargoFiltro, partidoFiltro, textoFiltro);

        model.addAttribute("candidatos", candidatos);
        model.addAttribute("totalEncontrado", candidatos.size());
        model.addAttribute("cargos", candidatosTseService.listarCargos());
        model.addAttribute("partidos", candidatosTseService.listarPartidos());
        model.addAttribute("cargoSelecionado", cargoFiltro);
        model.addAttribute("partidoSelecionado", partidoFiltro);
        model.addAttribute("textoSelecionado", textoFiltro);

        return "index";
    }
}
