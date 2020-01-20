package fr.houseofcode.unitconverter.controller;

import javax.measure.UnitConverter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.unitconverter.entity.AdditionalUnits;
import si.uom.SI;

@RestController
public class Controller {

    @RequestMapping("/convert")
    public void main(String[] args) {

        UnitConverter m2 = SI.SQUARE_METRE.getConverterTo(AdditionalUnits.HECTARE);
        UnitConverter ha = AdditionalUnits.HECTARE.getConverterTo(SI.SQUARE_METRE);
        double value1 = m2.convert(4000);
        double value2 = ha.convert(4000);

        //System.out.println(m2); //not working with exception
        //System.out.println(hectare); //not working with exception
        //System.out.println(toto);
        System.out.println(m2);
        System.out.println(value1);

        System.out.println(m2);
        System.out.println(value2);

        //System.out.println(m2.divide(hectare));//not working with exception
        //System.out.println(m2.divide(hectare).getValue());
        //System.out.println(m2.divide(hectare).getUnit());

    }

}
