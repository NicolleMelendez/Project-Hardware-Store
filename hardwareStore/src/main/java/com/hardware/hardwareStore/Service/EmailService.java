package com.hardware.hardwareStore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String toEmail, String token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(toEmail);
            helper.setSubject("Restablecer Contraseña - Hardware Store");
            String resetUrl = "http://localhost:8080/reset-password?token=" + token;
            String htmlContent = "<p>Hola,</p>"
                    + "<p>Has solicitado restablecer tu contraseña. Por favor, haz clic en el siguiente botón para continuar:</p>"
                    + "<a href=\"" + resetUrl + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #007bff; color: #ffffff; text-decoration: none; border-radius: 5px;\">Presione aquí</a>"
                    + "<p style=\"margin-top: 20px;\">Este enlace expirará en 24 horas. Si no solicitaste este cambio, por favor ignora este correo.</p>"
                    + "<p>Saludos,<br/>Equipo de Hardware Store</p>";

            helper.setText(htmlContent, true); // El 'true' indica que el contenido es HTML

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
}