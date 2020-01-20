package fr.houseofcode.unitconverter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.houseofcode.unitconverter.service.UnitConverterService;

@Controller
public class ConvertController {

    @Autowired
    private UnitConverterService unitConverterService;

    @RequestMapping("/convert")
    public String unitConverter(Model model, @RequestParam(required = false) double value) {
        Map<String, String> map = new HashMap<>();
        map.put("spring", "mvc");
        model.addAttribute("result", unitConverterService.meterToHectare(value));
        model.mergeAttributes(map);
        return "welcome";
        }

    //    UnitConverter converter = sourceUnit.getConverterTo(targetUnit);
}
