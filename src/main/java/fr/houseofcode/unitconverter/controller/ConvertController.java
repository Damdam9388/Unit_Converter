package fr.houseofcode.unitconverter.controller;

import fr.houseofcode.unitconverter.entity.InputContent;
import fr.houseofcode.unitconverter.exceptions.UnitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.houseofcode.unitconverter.service.UnitConverterService;

import javax.validation.Valid;

@Controller
public class ConvertController {
    private UnitConverterService unitConverterService;

    public ConvertController(@Autowired UnitConverterService unitConverterService){
        this.unitConverterService = unitConverterService;
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    public String valueException(){
        return "BindException";
    }

    @ExceptionHandler({UnitException.class})
    @ResponseBody
    public String unitExceptionHandler(UnitException uE){
        return uE.getMessage();
    }

    @GetMapping("/convert")
    public String unitForm(Model model){
        InputContent res = new InputContent();
        model.addAttribute("result", res);
        return "Converter";
    }

    @PostMapping("/convert")
    public String unitConverter(Model model, @Valid @ModelAttribute("data")InputContent data, BindingResult bindingResult) {
        InputContent res2 = new InputContent();
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage", "Veuillez corriger les erreurs suivantes :");
            model.addAttribute("bindingResult", bindingResult);
            return "Converter";
        } else if (data.getValue() > 0 && !bindingResult.hasErrors()){
            Double res = null;
            if (data.getInputState().equals("m2") && data.getOutputState().equals("hectare")) {
                res = unitConverterService.meterToHectare(data.getValue());
            } else if (data.getInputState().equals("Kw") && data.getOutputState().equals("Co2")) {
                res = unitConverterService.kwatttoco2(data.getValue());
            }

            res2.setInputState(data.getInputState());
            res2.setOutputState(data.getOutputState());
            res2.setValue(res);
            model.addAttribute("dataValue", data.getValue());
            model.addAttribute("inputUnit", data.getInputState());
            model.addAttribute("outputUnit", data.getOutputState());
            model.addAttribute("result", res2);
        }else if(data.getValue() < 0){
            model.addAttribute("negativeNumber", "you cannot insert negative number");
        }

        return "Converter";
    }
}
