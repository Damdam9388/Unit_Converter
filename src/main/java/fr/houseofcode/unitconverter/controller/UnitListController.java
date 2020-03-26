package fr.houseofcode.unitconverter.controller;

import fr.houseofcode.unitconverter.entity.datamodel.Unity;
import fr.houseofcode.unitconverter.entity.datarepository.UnityRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UnitListController {

    private UnityRespository unityRespository;

    public UnitListController(@Autowired UnityRespository unityRespository){
        this.unityRespository = unityRespository;
    }

    @GetMapping("/unitList")
    public String showUnities(Model model){
        model.addAttribute("unities", unityRespository.findAll());
        return "UnityList";
    }

    @GetMapping("/addUnit")
    public String addUnit(Unity unity){
       return "AddUnit";
    }

    @PostMapping("/saveUnit")
    public String saveUnit(@Valid @ModelAttribute("unity") Unity unity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "AddUnit";
        }

        unityRespository.save(unity);
        model.addAttribute("unities", unityRespository.findAll());
        return "Welcome";
    }

    @GetMapping("/edit/{id}")
    public String editUnit(@PathVariable("id") long id, Model model){
        Unity unity = unityRespository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid unity Id:" + id));
        model.addAttribute("unit", unity);
        return "EditUnit";
    }

    @PostMapping("/edit/{id}")
    public String updateUnit(@PathVariable("id") long id, @Valid Unity unity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            unity.setId(id);
            return "EditUnit";
        }

        unityRespository.save(unity);
        model.addAttribute("unities", unityRespository.findAll());
        return "UnityList";
    }

    @GetMapping("/delete/{id}")
    public String deleteUnit(@PathVariable("id") long id, Model model){
        Unity unity = unityRespository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid unit Id:" + id));
        unityRespository.delete(unity);
        model.addAttribute("unit", unityRespository.findAll());
        return "Welcome";
    }
}
