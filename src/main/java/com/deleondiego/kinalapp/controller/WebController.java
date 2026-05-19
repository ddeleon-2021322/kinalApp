package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.Usuario;
import com.deleondiego.kinalapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // IMPORTANTE: Importar para la seguridad
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/menu")
    public String home(Authentication authentication, Model model) {
        boolean esAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("esAdmin", esAdmin);

        model.addAttribute("SuccessMessage", "Conexion Establecida con exito");

        return "menuPrincipal";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/login?success";
    }
}