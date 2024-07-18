package com.kimenFen.cl.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/profesor/menu")
    public String profesorMenu() {
        return "profesor-menu";
    }
    
    @GetMapping("/apoderado/menu")
    public String apoderadoMenu() {
        return "apoderado-menu";
    }

    @GetMapping("/administrador/menu")
    public String administradorMenu() {
        return "administrador-menu";
    }



}
