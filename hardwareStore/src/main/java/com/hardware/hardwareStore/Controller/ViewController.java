package com.hardware.hardwareStore.Controller;

import com.hardware.hardwareStore.Repository.*;
import com.hardware.hardwareStore.Security.CustomUserDetails;
import com.hardware.hardwareStore.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import org.springframework.ui.Model;

@Controller
public class ViewController {

    /* -------------------- DASHBOARD -------------------- */
    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("currentUser", userDetails);
            model.addAttribute("userName", userDetails.getName()); // Si agregaste el método getName()
        }
        return "index";
    }








}
