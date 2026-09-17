package br.com.pucminas.auroralogin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterForm {

    @NotBlank(message = "Informe um nome de usuário.")
    @Size(min = 3, max = 30, message = "O usuário deve ter entre 3 e 30 caracteres.")
    private String username;

    @NotBlank(message = "Informe um email.")
    @Email(message = "Informe um email válido.")
    private String email;

    @NotBlank(message = "Informe uma senha.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String password;

    @NotBlank(message = "Confirme a senha.")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
