package fr.houseofcode.unitconverter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.houseofcode.unitconverter.entity.InputContent;
import fr.houseofcode.unitconverter.service.UnitConverterService;

@Controller
public class ConvertController {
    private UnitConverterService unitConverterService;

    public ConvertController(@Autowired UnitConverterService unitConverterService){
        this.unitConverterService = unitConverterService;
    }

    @GetMapping("/convert")
    public String unitForm(){
        return "Welcome";
    }

    @PostMapping("/convert")
    public String unitConverter(Model model, @Valid @ModelAttribute("data")InputContent data) {
        if (data.getValue() != null){
            Double res = null;
            //TODO Vérifier le output ?

            if (data.getInputState().equals("m2")) {
                res = unitConverterService.meterToHectare(data.getValue());
            } else if (data.getInputState().equals("Kw")) {
                res = unitConverterService.kwatttoco2(data.getValue());
            }

            model.addAttribute("result", res);

        }
        return "Welcome";
    }
}
