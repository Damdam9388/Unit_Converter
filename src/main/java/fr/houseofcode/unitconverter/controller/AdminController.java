package fr.houseofcode.unitconverter.controller;

import fr.houseofcode.unitconverter.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
    private HistoryService historyService;

    public AdminController(@Autowired HistoryService historyService) {
        this.historyService = historyService;
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String initData() {
        historyService.init();
        return "init success";
    }
}
