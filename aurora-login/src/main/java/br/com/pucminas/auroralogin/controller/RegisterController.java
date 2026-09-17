package br.com.pucminas.auroralogin.controller;

import br.com.pucminas.auroralogin.dto.RegisterForm;
import br.com.pucminas.auroralogin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String exibirFormulario(Model model) {
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        return "register";
    }

    @PostMapping("/register")
    public String processarCadastro(@Valid @ModelAttribute("registerForm") RegisterForm form,
                                     BindingResult result,
                                     Model model) {


        if (!form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "senha.diferente", "As senhas não coincidem.");
        }

        if (userService.existsByUsername(form.getUsername())) {
            result.rejectValue("username", "usuario.duplicado", "Esse nome de usuário já está em uso.");
        }

        if (userService.existsByEmail(form.getEmail())) {
            result.rejectValue("email", "email.duplicado", "Esse email já está cadastrado.");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.register(form);

        return "redirect:/login?registered";
    }
}
