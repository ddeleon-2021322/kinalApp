package com.deleondiego.kinalapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuPrincipalController {

    @GetMapping("/menu")
        public String menuprincipal(){
        return "menuprincipal";
    }
}
