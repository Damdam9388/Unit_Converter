package fr.houseofcode.unitconverter.controller;

import fr.houseofcode.unitconverter.service.HistoryService;
import fr.houseofcode.unitconverter.service.UnitConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
    private HistoryService historyService;
    private UnitConverterService unitConverterService;

    public AdminController(@Autowired HistoryService historyService,
                           @Autowired UnitConverterService unitConverterService) {
        this.historyService = historyService;
        this.unitConverterService = unitConverterService;
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String initData() {
        historyService.init();
        unitConverterService.init();

        return "init success";
    }
}
