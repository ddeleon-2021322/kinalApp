package com.deleondiego.kinalapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String user, @RequestParam String pass) {
        if (user.equals("admin") && pass.equals("kinal123")) {
            return "menu";
        } else {
            return "redirect:/login?error";
        }
    }
}
