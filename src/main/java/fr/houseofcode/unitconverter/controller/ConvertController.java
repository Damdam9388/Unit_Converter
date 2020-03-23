package fr.houseofcode.unitconverter.controller;

import fr.houseofcode.unitconverter.entity.*;
import fr.houseofcode.unitconverter.exceptions.UnitException;
import fr.houseofcode.unitconverter.service.ErrorsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.houseofcode.unitconverter.service.UnitConverterService;
import org.springframework.web.servlet.ModelAndView;
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
    public String unitExceptionHandler(UnitException uE, Model model){
        //uE.getMessage();
        InputContent res = new InputContent();

        String key = BindingResult.MODEL_KEY_PREFIX + "convert";
        BindingResult bindingResult = (BindingResult) model.getAttribute(key);
        ObjectError error = new ObjectError("outputState", uE.getMessage());
        bindingResult.addError(error);

        errorsApi.bindingResultError(model, bindingResult);
        model.addAttribute("result", res);
        return "Converter";
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
    public String unitConverter(Model model, @Valid @ModelAttribute("data")InputContent data, BindingResult bindingResult) throws Exception {
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

    private Double calculMethodToChoose(InputContent data) throws Exception {
        Double res = null;
        if (data.getInputState().equals("m²") && data.getOutputState().equals("ha")) {
            res = unitConverterService.convert(data.getValue(), SI.SQUARE_METRE, AdditionalUnits.HECTARE);
        }else if(data.getInputState().equals("ha") && data.getOutputState().equals("m²")) {
            res = unitConverterService.convert(data.getValue(), AdditionalUnits.HECTARE, SI.SQUARE_METRE);
        }else if(data.getInputState().equals("km²") && data.getOutputState().equals("m²")) {
            res = unitConverterService.convert(data.getValue(), AdditionalUnits.SQUARE_KILOMETER, SI.SQUARE_METRE);
        }else if(data.getInputState().equals("m²") && data.getOutputState().equals("km²")) {
            res = unitConverterService.convert(data.getValue(), SI.SQUARE_METRE, AdditionalUnits.SQUARE_KILOMETER);
        } else if(data.getInputState().equals("km²") && data.getOutputState().equals("ha")) {
            res = unitConverterService.convert(data.getValue(), AdditionalUnits.SQUARE_KILOMETER, AdditionalUnits.HECTARE);
        } else if (data.getInputState().equals("ha") && data.getOutputState().equals("km²")) {
            res = unitConverterService.convert(data.getValue(), AdditionalUnits.HECTARE, AdditionalUnits.SQUARE_KILOMETER);
        } else if (data.getInputState().equals("kW") && data.getOutputState().equals("Co²")) {
            res = unitConverterService.convertKwattCo2(data.getValue(), data);
        } else if (data.getInputState().equals("Co²") && data.getOutputState().equals("kW")) {
            res = unitConverterService.convertKwattCo2(data.getValue(), data);
        } else{
            throw new UnitException("oups marche pas");
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
