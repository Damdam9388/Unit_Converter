package fr.houseofcode.unitconverter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String showWelcomeView(){
        return "Welcome";
    }
}
