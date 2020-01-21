package fr.houseofcode.unitconverter.controller;

import fr.houseofcode.unitconverter.entity.InputContent;
import fr.houseofcode.unitconverter.exceptions.unitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import fr.houseofcode.unitconverter.service.UnitConverterService;

import javax.measure.UnitConverter;
import javax.validation.Valid;
import java.sql.SQLException;

@Controller
public class ConvertController {
    private UnitConverterService unitConverterService;

    public ConvertController(@Autowired UnitConverterService unitConverterService){
        this.unitConverterService = unitConverterService;
    }

    @ExceptionHandler({BindException.class})
    public String valueException(){
        return "BindException";
    }

    @GetMapping("/convert")
    public String unitForm(){
        return "Welcome";
    }

    @PostMapping("/convert")
    public String unitConverter(Model model, @Valid @ModelAttribute("data")InputContent data) {
        if (data.getValue() > 0){
            model.addAttribute("result", unitConverterService.meterToHectare(data.getValue()));
        }else if(data.getValue() < 0){
            throw new unitException("you cannot insert a negative number");
        }
        return "Welcome";
    }
}
