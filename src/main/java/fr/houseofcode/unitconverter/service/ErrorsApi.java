package fr.houseofcode.unitconverter.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorsApi {

    public void negativeNumberError(Model model){
        model.addAttribute("negativeNumber", "you cannot insert negative number");
    }

    public void bindingResultError(Model model, BindingResult bindingResult){
        List<String> errors = new ArrayList();
        for (ObjectError data : bindingResult.getAllErrors()) {
            errors.add(data.getDefaultMessage());
        }

        model.addAttribute("errorMessage", "Veuillez corriger les erreurs suivantes :");
        model.addAttribute("errors", errors);
    }
    public void exeptionError(Exception e, ModelAndView modelAndView) {
        List<String> errors = new ArrayList();
        errors.add(e.getMessage());
        modelAndView.addObject("errorMessage", "Veuillez corriger les erreurs suivantes :");
        modelAndView.addObject("errors", errors);
    }
}
