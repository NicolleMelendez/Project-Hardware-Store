package com.hardware.hardwareStore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource; // Importa esta clase
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


            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Restablecer Contraseña - ForgeMax");
            String resetUrl = "http://localhost:8080/reset-password?token=" + token;

            String htmlContent = "<!DOCTYPE html>"
                    + "<html lang='es'>"
                    + "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f7f6;'>"
                    + "  <table border='0' cellpadding='0' cellspacing='0' width='100%'>"
                    + "    <tr>"
                    + "      <td style='padding: 20px 0;'>"
                    + "        <table align='center' border='0' cellpadding='0' cellspacing='0' width='600' style='border-collapse: collapse; background-color: #ffffff; box-shadow: 0 4px 15px rgba(0,0,0,0.1); border-radius: 8px; border-top: 5px solid #FFC107;'>"
                    + "          "
                    + "          <tr>"
                    + "            <td align='center' style='padding: 40px 0 30px 0;'>"
                    // ✅ CAMBIO 2: Añadimos la etiqueta <img> para mostrar el logo.
                    + "              <img src='cid:logo' alt='Logo ForgeMax' style='display: block; width: 80px; height: 80px; border-radius: 50%; border: 3px solid #005A9C;'/>"
                    + "              <h1 style='color: #005A9C; margin: 20px 0 0 0; font-size: 24px;'>Solicitud de Cambio de Contraseña</h1>"
                    + "            </td>"
                    + "          </tr>"
                    + "          "
                    + "          <tr>"
                    + "            <td style='padding: 0 40px;'>"
                    + "              <p style='margin: 0; color: #343A40; font-size: 16px; line-height: 24px;'>Hola,</p>"
                    + "              <p style='margin: 20px 0; color: #343A40; font-size: 16px; line-height: 24px;'>Hemos recibido una solicitud para restablecer la contraseña de tu cuenta. Para continuar, haz clic en el siguiente botón:</p>"
                    + "            </td>"
                    + "          </tr>"
                    + "          "
                    + "          <tr>"
                    + "            <td align='center' style='padding: 20px 40px;'>"
                    + "              <a href='" + resetUrl + "' target='_blank' style='display: inline-block; padding: 15px 30px; font-size: 16px; font-weight: bold; color: #ffffff; background-color: #005A9C; text-decoration: none; border-radius: 5px;'>Restablecer Contraseña</a>"
                    + "            </td>"
                    + "          </tr>"
                    + "          "
                    + "          <tr>"
                    + "            <td style='padding: 0 40px 40px 40px;'>"
                    + "              <p style='margin: 20px 0; color: #343A40; font-size: 16px; line-height: 24px;'>Si no solicitaste este cambio, puedes ignorar este correo de forma segura.</p>"
                    + "              <p style='margin: 0; color: #6c757d; font-size: 14px;'>Gracias,<br/>El equipo de ForgeMax</p>"
                    + "            </td>"
                    + "          </tr>"
                    + "          "
                    + "          <tr>"
                    + "            <td align='center' style='padding: 20px 40px; background-color: #f8f9fa; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>"
                    + "              <p style='margin: 0; color: #6c757d; font-size: 12px;'>© 2025 ForgeMax. Todos los derechos reservados.</p>"
                    + "            </td>"
                    + "          </tr>"
                    + "        </table>"
                    + "      </td>"
                    + "    </tr>"
                    + "  </table>"
                    + "</body>"
                    + "</html>";

            helper.setText(htmlContent, true);

            // ✅ CAMBIO 3: Adjuntamos el logo como un recurso "inline".
            // Le damos un identificador "logo" que coincide con el "cid:logo" del HTML.
            ClassPathResource logoResource = new ClassPathResource("static/assets/img/logoo.png");
            helper.addInline("logo", logoResource);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
}