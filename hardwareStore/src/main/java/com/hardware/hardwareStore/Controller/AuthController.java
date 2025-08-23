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

    // Nuevos endpoints para "Recordar Contraseña"
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordAction(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            String token = authService.createPasswordResetToken(email);
            // Lógica para enviar el email con el token.
            // Por simplicidad, aquí solo mostramos el mensaje. En un proyecto real,
            // se usaría un servicio de correo.
            System.out.println("Token de restablecimiento para " + email + ": " + token);
            redirectAttributes.addFlashAttribute("msg", "Se ha enviado un enlace de restablecimiento a tu email.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        try {
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
            redirectAttributes.addAttribute("token", token);
            return "redirect:/reset-password";
        }
        try {
            authService.updatePassword(token, password);
            redirectAttributes.addFlashAttribute("msg", "Tu contraseña ha sido actualizada exitosamente.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("token", token);
            return "redirect:/reset-password";
        }
    }
}