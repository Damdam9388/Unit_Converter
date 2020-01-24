package fr.houseofcode.unitconverter.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class ErrorsApi {

    public void negativeNumberError(Model model){
        model.addAttribute("negativeNumber", "you cannot insert negative number");
    }

    public void bindingResultError(Model model, BindingResult bindingResult){
        model.addAttribute("errorMessage", "Veuillez corriger les erreurs suivantes :");
        model.addAttribute("bindingResult", bindingResult);
    }
}
