package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador de la página de inicio.
 *
 * @author Mario
 * @version 1.0
 */
@Controller
public class HomeController {

    /**
     * Muestra la página de inicio.
     */
    @GetMapping("/")
    public String home(){
        return "index";
    }
}
