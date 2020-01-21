package fr.houseofcode.unitconverter.controller;

import java.util.HashMap;
import java.util.Map;

import fr.houseofcode.unitconverter.entity.InputContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.houseofcode.unitconverter.service.UnitConverterService;

import javax.validation.Valid;

@Controller
public class ConvertController {

    @Autowired
    private UnitConverterService unitConverterService;

    @RequestMapping("/convert")
    public String unitConverter(Model model, @Valid @ModelAttribute("data")InputContent data, BindingResult result) {
        if(data == null && result.hasErrors()){
            return "Welcome";
        }
        Map<String, String> map = new HashMap<>();
        map.put("spring", "mvc");
        if (data.getValue() != null){
            model.addAttribute("result", unitConverterService.meterToHectare(data.getValue()));
        }

        model.mergeAttributes(map);
        return "Welcome";

        }

    //    UnitConverter converter = sourceUnit.getConverterTo(targetUnit);
}
