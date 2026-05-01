package com.deleondiego.kinalapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/menu")
    public String home(Model model){
        model.addAttribute("SuccessMessage", "Conexion Establecida con exito");
                return "menuPrincipal";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}

