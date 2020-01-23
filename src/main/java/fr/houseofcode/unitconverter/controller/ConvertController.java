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
import java.util.ArrayList;

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

//        List<String> allInData = new ArrayList<String>();
//        allInData.add("M²");
//        res.setAllInputData(allInData);



        model.addAttribute("result", res);
        return "Converter";
    }

    @PostMapping("/convert")
    public String unitConverter(Model model, @Valid @ModelAttribute("data")InputContent data, BindingResult bindingResult) {
        InputContent res2 = new InputContent();
        if(bindingResult.hasErrors()){
            bindingResultError(model, bindingResult);
            return "Converter";
        } else if (data.getValue() > 0 && !bindingResult.hasErrors()){
            Double res = calculMethodToChoose(data);

            res2.setInputState(data.getInputState());
            res2.setOutputState(data.getOutputState());
            res2.setValue(res);

            addAttributeToModel(model, data, res2);
        }else if(data.getValue() < 0 && !bindingResult.hasErrors()){
            negativeNumberError(model);
        }

        return "Converter";
    }

    private Double calculMethodToChoose(InputContent data){
        Double res = null;
        if (data.getInputState().equals("m2") && data.getOutputState().equals("hectare")) {
            res = unitConverterService.meterToHectare(data.getValue());
        } else if (data.getInputState().equals("Kw") && data.getOutputState().equals("Co2")) {
            res = unitConverterService.kwatttoco2(data.getValue());
        } else if (data.getInputState().equals("hectare") && data.getOutputState().equals("m2")) {
            res = unitConverterService.hectareToMeter(data.getValue());
        } else if (data.getInputState().equals("Co2") && data.getOutputState().equals("Kw")) {
            res = unitConverterService.co2ToKwatt(data.getValue());
        }

        return res;
    }

    private void negativeNumberError(Model model){
        model.addAttribute("negativeNumber", "you cannot insert negative number");
    }

    private void bindingResultError(Model model, BindingResult bindingResult){
        model.addAttribute("errorMessage", "Veuillez corriger les erreurs suivantes :");
        model.addAttribute("bindingResult", bindingResult);
    }

    private void addAttributeToModel(Model model, InputContent data, InputContent res2){
        model.addAttribute("dataValue", data.getValue());
        model.addAttribute("inputUnit", data.getInputState());
        model.addAttribute("outputUnit", data.getOutputState());
        model.addAttribute("result", res2);
    }
}
