package fr.houseofcode.unitconverter.controller;

import fr.houseofcode.unitconverter.entity.*;
import fr.houseofcode.unitconverter.exceptions.UnitException;
import fr.houseofcode.unitconverter.service.ErrorsApi;
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
import si.uom.SI;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class ConvertController {
    private UnitConverterService unitConverterService;
    private ErrorsApi errorsApi;
    private UnityRespository unityRespository;

    public ConvertController(@Autowired UnitConverterService unitConverterService, @Autowired ErrorsApi errorsApi, @Autowired UnityRespository unityRespository){
        this.unitConverterService = unitConverterService;
        this.errorsApi = errorsApi;
        this.unityRespository = unityRespository;
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

    @ModelAttribute("unitList")
    public Collection<SymbolOnly> loadAllInputData(){
        Collection<SymbolOnly> symbols;
        symbols = unityRespository.findAllSummarizedBy();
        return symbols;
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
            errorsApi.bindingResultError(model, bindingResult);
            model.addAttribute("result",data);
            return "Converter";

        } else if(data.getValue() < 0 && !bindingResult.hasErrors()) {
            errorsApi.negativeNumberError(model);
            model.addAttribute("result", data);
            return "Converter";

        }else if(data.getValue() > 0 && !bindingResult.hasErrors()){
            Double res = calculMethodToChoose(data);

            res2.setInputState(data.getInputState());
            res2.setOutputState(data.getOutputState());
            res2.setValue(res);

            addAttributeToModel(model, data, res2);

        } else {
            model.addAttribute("result", data);

        }
        return "Converter";
    }

    private Double calculMethodToChoose(InputContent data){
        Double res = null;
        if (data.getInputState().equals("m2") && data.getOutputState().equals("hectare")) {
            res = unitConverterService.convert(data.getValue(), SI.SQUARE_METRE, AdditionalUnits.HECTARE);
        }else if(data.getInputState().equals("hectare") && data.getOutputState().equals("m2")) {
            res = unitConverterService.convert(data.getValue(), AdditionalUnits.HECTARE, SI.SQUARE_METRE);
        } else if (data.getInputState().equals("Kw") && data.getOutputState().equals("Co2")) {
            res = unitConverterService.convertKwattCo2(data.getValue(), data);
        } else if (data.getInputState().equals("Co2") && data.getOutputState().equals("Kw")) {
            res = unitConverterService.convertKwattCo2(data.getValue(), data);
        }

        return res;
    }

    private void addAttributeToModel(Model model, InputContent data, InputContent res2){
        model.addAttribute("dataValue", data.getValue());
        model.addAttribute("inputUnit", data.getInputState());
        model.addAttribute("outputUnit", data.getOutputState());
        model.addAttribute("result", res2);
    }
}
