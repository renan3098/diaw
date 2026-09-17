package br.com.pucminas.auroralogin.controller;

import br.com.pucminas.auroralogin.dto.RecoverPasswordForm;
import br.com.pucminas.auroralogin.dto.ResetPasswordForm;
import br.com.pucminas.auroralogin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordRecoveryController {

    private final UserService userService;

    public PasswordRecoveryController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/recoverpassword")
    public String exibirFormularioRecuperacao(Model model) {
        if (!model.containsAttribute("recoverPasswordForm")) {
            model.addAttribute("recoverPasswordForm", new RecoverPasswordForm());
        }
        return "recoverpassword";
    }

    @PostMapping("/recoverpassword")
    public String processarRecuperacao(@Valid @ModelAttribute("recoverPasswordForm") RecoverPasswordForm form,
                                        BindingResult result,
                                        Model model) {
        if (result.hasErrors()) {
            return "recoverpassword";
        }

        userService.processPasswordRecovery(form.getEmail());

        model.addAttribute("successMessage",
                "Se o email informado estiver cadastrado, você receberá as instruções de recuperação em instantes.");
        model.addAttribute("recoverPasswordForm", new RecoverPasswordForm());
        return "recoverpassword";
    }

    @GetMapping("/resetpassword")
    public String exibirFormularioReset(@RequestParam("token") String token, Model model) {
        if (!userService.isResetTokenValid(token)) {
            model.addAttribute("tokenInvalido", true);
            return "resetpassword";
        }

        ResetPasswordForm form = new ResetPasswordForm();
        form.setToken(token);
        model.addAttribute("resetPasswordForm", form);
        return "resetpassword";
    }

    @PostMapping("/resetpassword")
    public String processarReset(@Valid @ModelAttribute("resetPasswordForm") ResetPasswordForm form,
                                  BindingResult result,
                                  Model model) {

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "senha.diferente", "As senhas não coincidem.");
        }

        if (!userService.isResetTokenValid(form.getToken())) {
            model.addAttribute("tokenInvalido", true);
            return "resetpassword";
        }

        if (result.hasErrors()) {
            return "resetpassword";
        }

        userService.resetPassword(form.getToken(), form.getPassword());

        return "redirect:/login?passwordReset";
    }
}
