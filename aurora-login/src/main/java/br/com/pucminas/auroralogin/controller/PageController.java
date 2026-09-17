package br.com.pucminas.auroralogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            @RequestParam(value = "passwordReset", required = false) String passwordReset,
            Model model) {

        if (error != null) {
            model.addAttribute("loginError", "Usuário/email ou senha inválidos.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Sessão encerrada com sucesso.");
        }
        if (registered != null) {
            model.addAttribute("registeredMessage", "Cadastro realizado com sucesso! Faça login.");
        }
        if (passwordReset != null) {
            model.addAttribute("registeredMessage", "Senha redefinida com sucesso! Faça login.");
        }

        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
