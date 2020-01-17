package fr.houseofcode.unitconverter.controller;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Area;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import si.uom.SI;
import tec.units.ri.quantity.Quantities;
import tec.uom.se.unit.ProductUnit;
import tec.uom.se.unit.Units;

@RestController
public class controller {

    @RequestMapping("")
    public void main(String[] args) {

        Quantity<Area> m2 = Quantities.getQuantity(4000.0, SI.SQUARE_METRE);

        /**
         * The SI unit for area quantities (standard name <code>m2</code>).
         */
        Unit<Area> hectare = addUnit(new ProductUnit<>(Units.SQUARE_METRE.divide(10000)), Area.class);

        //	        Quantity<Area> m2 = Quantities.getQuantity(4000.0, SI.SQUARE_METRE);

        System.out.println(m2); //not working with exception
        System.out.println(hectare); //not working with exception
        System.out.println(m2.divide(hectare));//not working with exception

        System.out.println(m2.divide(hectare).getValue());
        System.out.println(m2.divide(hectare).getUnit());

    }

}
