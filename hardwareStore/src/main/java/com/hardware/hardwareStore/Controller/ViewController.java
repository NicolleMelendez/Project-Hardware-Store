package com.hardware.hardwareStore.Controller;


import ch.qos.logback.core.model.Model;

import com.hardware.hardwareStore.model.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

<<<<<<< HEAD
<<<<<<< Updated upstream
    // Ruta: http://localhost:8080/
    @GetMapping("/")
=======
    @GetMapping({"/", "/index"})
>>>>>>> origin/sebastian
    public String home() {
        return "index";
    }






=======
//rutas vistas
>>>>>>> Stashed changes
}
