package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Usuario o contraseña incorrectos");
        if (logout != null) model.addAttribute("msg", "Has cerrado sesión");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerAction(@RequestParam String name,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 Model model) {
        try {
            authService.registerUser(name, email, password, "EMPLOYEE"); // rol por defecto
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";
    }

    /**
     * Procesa la solicitud para restablecer la contraseña.
     */
    @PostMapping("/forgot-password")
    public String forgotPasswordAction(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            // ✅ PASO 1: Verificamos si ya hay un token activo para este correo.
            if (authService.tokenExistsForEmail(email)) {

                // ✅ PASO 2: Si existe, mostramos un mensaje amigable y redirigimos.
                // No se crea un nuevo token ni se envía un nuevo correo.
                redirectAttributes.addFlashAttribute("error", "Ya tienes una solicitud de restablecimiento activa. Por favor, revisa tu bandeja de entrada.");
                return "redirect:/forgot-password";
            }

            // ✅ PASO 3: Si no existe un token, procedemos a crearlo y enviarlo.
            authService.createPasswordResetToken(email);
            redirectAttributes.addFlashAttribute("msg", "Se ha enviado un enlace de restablecimiento a tu correo. ¡Revisa tu bandeja de entrada!");
            return "redirect:/login";

        } catch (RuntimeException e) {
            // Este catch ahora solo se activará si el email no está registrado.
            redirectAttributes.addFlashAttribute("error", "El correo electrónico proporcionado no se encuentra registrado.");
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        try {
            // Validamos el token. Si es inválido, el método lanzará una excepción.
            authService.validatePasswordResetToken(token);
            model.addAttribute("token", token);
            return "auth/reset-password";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @PostMapping("/reset-password")
    public String resetPasswordAction(@RequestParam String token,
                                      @RequestParam String password,
                                      @RequestParam String confirmPassword,
                                      RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden.");
            // Adjuntamos el token como parámetro para que la URL se mantenga correcta
            redirectAttributes.addAttribute("token", token);
            return "redirect:/reset-password";
        }
        try {
            authService.updatePassword(token, password);
            redirectAttributes.addFlashAttribute("msg", "Tu contraseña ha sido actualizada exitosamente.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            // Adjuntamos el token para volver a la página de reseteo si hay error
            redirectAttributes.addAttribute("token", token);
            return "redirect:/reset-password";
        }
    }
}