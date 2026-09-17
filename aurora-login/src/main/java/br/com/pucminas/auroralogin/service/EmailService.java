package br.com.pucminas.auroralogin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${app.base-url}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordRecoveryEmail(String to, String token) {
        String link = baseUrl + "/resetpassword?token=" + token;

        if (!mailEnabled) {
            log.info("[MODO SIMULACAO] Email de recuperacao nao enviado de verdade.");
            log.info("[MODO SIMULACAO] Destinatario: {} | Link de redefinicao: {}", to, link);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Aurora Login - Recuperação de senha");
            message.setText(
                    "Olá!\n\n" +
                    "Recebemos uma solicitação para redefinir sua senha.\n" +
                    "Clique no link abaixo (válido por 30 minutos) para criar uma nova senha:\n\n" +
                    link + "\n\n" +
                    "Se você não solicitou isso, apenas ignore este email."
            );
            mailSender.send(message);
            log.info("Email de recuperacao enviado para {}", to);
        } catch (Exception e) {
            log.error("Falha ao enviar email de recuperacao para {}: {}", to, e.getMessage());
        }
    }
}
