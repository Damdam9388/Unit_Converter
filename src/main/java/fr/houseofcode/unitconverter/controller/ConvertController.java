package fr.houseofcode.unitconverter.controller;

import javax.measure.UnitConverter;

import fr.houseofcode.unitconverter.entity.InputContent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.unitconverter.entity.AdditionalUnits;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import si.uom.SI;

@Controller
public class ConvertController {

    @RequestMapping("/convert")
    @ResponseBody
    public String unitConverter(Model model, @RequestParam InputContent data){
//        UnitConverter m2 = SI.SQUARE_METRE.getConverterTo(AdditionalUnits.HECTARE);
        UnitConverter ha = AdditionalUnits.HECTARE.getConverterTo(SI.SQUARE_METRE);
//        double value1 = m2.convert(4000);
//        double value2 = ha.convert(data.getValue());
//        model.addAttribute("result", value2);

        return "Welcome";
    }

}
