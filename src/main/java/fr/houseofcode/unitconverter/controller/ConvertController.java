package fr.houseofcode.unitconverter.controller;

import fr.houseofcode.unitconverter.dto.HistoryData;
import fr.houseofcode.unitconverter.dto.InputContent;
import fr.houseofcode.unitconverter.entity.*;
import fr.houseofcode.unitconverter.entity.datarepository.UnityRespository;
import fr.houseofcode.unitconverter.exceptions.UnitException;
import fr.houseofcode.unitconverter.service.AdditionalUnits;
import fr.houseofcode.unitconverter.service.ErrorsApi;
import fr.houseofcode.unitconverter.service.HistoryService;
import org.apache.logging.log4j.LogManager;
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
import org.springframework.web.servlet.ModelAndView;
import si.uom.SI;
import javax.measure.Unit;
import javax.validation.Valid;
import java.util.Collection;

import org.apache.logging.log4j.Logger;

@Controller
public class ConvertController {
    Logger logger = LogManager.getLogger(ConvertController.class);

    private UnitConverterService unitConverterService;
    private ErrorsApi errorsApi;
    private HistoryService historyService;

    //TODO remove?
    private UnityRespository unityRespository;

    public ConvertController(@Autowired UnitConverterService unitConverterService, @Autowired ErrorsApi errorsApi, @Autowired HistoryService historyService, @Autowired UnityRespository unityRespository){
        this.unitConverterService = unitConverterService;
        this.errorsApi = errorsApi;
        this.historyService = historyService;
        //TODO remove?
        this.unityRespository = unityRespository;
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    public String valueException(){
        return "BindException";
    }

    @ExceptionHandler({UnitException.class, Exception.class})
    public ModelAndView unitExceptionHandler(Exception e){
        ModelAndView modelAndView = new ModelAndView();
        InputContent inputContent = new InputContent();

        modelAndView.setViewName("Converter");
        modelAndView.addObject("result", inputContent);
        modelAndView.addObject("unitList", loadAllInputData());
        errorsApi.exeptionError(e, modelAndView);
        return modelAndView;
    }

    @ModelAttribute("unitList")
    public Collection<SymbolOnly> loadAllInputData(){
        Collection<SymbolOnly> symbols;
        symbols = unityRespository.findAllSummarizedBy();
        return symbols;
    }

   /* *//**
    * We cannot use @modelAttribute because we need refresh <b>AFTER</b> post execution.
    *//*
    public HistoryData loadAllHistoryData(){
        HistoryData historyData = new HistoryData();
        historyData.setHistoryList(historyService.getAll());
        return historyData;
    }*/
    /**
     * We cannot use @modelAttribute because we need refresh <b>AFTER</b> post execution.
     */
    public HistoryData loadLast10HistoryData(){
        HistoryData historyData = new HistoryData();
        historyData.setHistoryList(historyService.getLastTen());
        return historyData;
    }


    @GetMapping("/convert")
    public String unitForm(Model model){
        logger.debug("affichage de la page de conversion");
        InputContent res = new InputContent();
        model.addAttribute("result", res);
        model.addAttribute("historyData", loadLast10HistoryData());

        return "Converter";
    }

    @PostMapping("/convert")
    public String unitConverter(Model model, @Valid @ModelAttribute("data")InputContent data, BindingResult bindingResult) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("conversion de ")
                .append(data.getValue())
                .append(' ')
                .append(data.getInputState())
                .append(" en ")
                .append(data.getOutputState())
                .append(" est en cours");

        logger.info(sb.toString());

        InputContent res2 = new InputContent();

        if(bindingResult.hasErrors()){
            errorsApi.bindingResultError(model, bindingResult);
            model.addAttribute("result",data);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("echec de la conversion de ")
                    .append(data.getValue())
                    .append(data.getInputState())
                    .append(" en")
                    .append(data.getOutputState());
            logger.warn(sb2.toString());
            return "Converter";

        } else if(data.getValue() < 0 && !bindingResult.hasErrors()) {
            errorsApi.negativeNumberError(model);
            model.addAttribute("result", data);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("echec de la conversion de ")
                    .append(data.getValue())
                    .append(data.getInputState())
                    .append(" en")
                    .append(data.getOutputState())
                    .append(", aucune valeur saisi");
            logger.info(sb3.toString());
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
        StringBuilder sb4 = new StringBuilder();
        sb4.append("conversion de ")
                .append(data.getValue())
                .append(data.getInputState())
                .append(" en ")
                .append(data.getOutputState())
                .append(" à le resultat ")
                .append(res2.getValue())
                .append(data.getOutputState());
        logger.info(sb4.toString());

        historyService.save(data, res2);
        model.addAttribute("historyData", loadLast10HistoryData());

        return "Converter";
    }

    private Double calculMethodToChoose(InputContent data) {
        Double res = null;
        Boolean hasError = false;
        Unit srcUnit = fromString(data.getInputState());
        Unit targetUnit = fromString(data.getOutputState());

        if (data.getInputState().equals(data.getOutputState())) {
            UnitException uE = new UnitException("veuillez chosir une source differente de la destination");
            logger.error("echec de la conversion", uE);
            throw  uE;
        }
        try {
            res = unitConverterService.convert(data.getValue(), srcUnit, targetUnit);
        } catch (NullPointerException e) {
            hasError = true;
        }
        if (hasError) {
            if (data.getInputState().equals("kW") && data.getOutputState().equals("Co²")) {
                res = unitConverterService.convertKwattCo2(data.getValue(), data);
            } else if (data.getInputState().equals("Co²") && data.getOutputState().equals("kW")) {
                res = unitConverterService.convertKwattCo2(data.getValue(), data);
            } else{
                UnitException uE = new UnitException("on ne sais pas convertir !!");
                logger.error("Conversion impossible", uE);
                throw uE;
            }
        }
        return res;
    }

    private void addAttributeToModel(Model model, InputContent data, InputContent res2){
        model.addAttribute("dataValue", data.getValue());
        model.addAttribute("inputUnit", data.getInputState());
        model.addAttribute("outputUnit", data.getOutputState());
        model.addAttribute("result", res2);
    }

    private Unit fromString(String unitStr){
        Unit res = null;
        if (unitStr.equals("m²")) {
            res = SI.SQUARE_METRE;
        } else if(unitStr.equals("ha")){
            res = AdditionalUnits.HECTARE;
        } else if(unitStr.equals("km²")) {
            res = AdditionalUnits.SQUARE_KILOMETER;
        } else if(unitStr.equals("kW")) {
            //FIXME create Unit;
            res =null;
        } else if(unitStr.equals("Co²")) {
            //FIXME create Unit;
            res =null;
        }
        return res;
    }
}
