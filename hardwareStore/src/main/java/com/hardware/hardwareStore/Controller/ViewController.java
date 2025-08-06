package com.hardware.hardwareStore.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

<<<<<<< Updated upstream
    // Ruta: http://localhost:8080/
    @GetMapping("/")
    public String home() {
        return "index"; // Busca index.html en src/main/resources/templates
    }

    @GetMapping("/pages-sign-in")
    public String login() {
        return "pages-sign-in";
    }

    @GetMapping("/pages-sign-up")
    public String register() {
        return "pages-sign-up";
    }

    @GetMapping("/pages-profile")
    public String profile() {
        return "pages-profile";
    }

=======
//rutas vistas
>>>>>>> Stashed changes
}
