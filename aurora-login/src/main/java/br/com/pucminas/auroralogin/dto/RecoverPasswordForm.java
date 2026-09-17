package br.com.pucminas.auroralogin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RecoverPasswordForm {

    @NotBlank(message = "Informe seu email.")
    @Email(message = "Informe um email válido.")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
