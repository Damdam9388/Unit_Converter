/**
 * 
 */
package fr.houseofcode.unitconverter.service;

import javax.measure.UnitConverter;

import org.springframework.stereotype.Service;

import fr.houseofcode.unitconverter.entity.AdditionalUnits;
import si.uom.SI;

/**
 * @author dte
 *
 */
@Service
public class UnitConverterService {

    public double meterToHectare(double value) {
        UnitConverter m2 = SI.SQUARE_METRE.getConverterTo(AdditionalUnits.HECTARE);
        double value1 = m2.convert(value);
        return value1;
    }

    public double hectareToMeter(double value) {
        UnitConverter ha = AdditionalUnits.HECTARE.getConverterTo(SI.SQUARE_METRE);
        double value2 = ha.convert(value);
        return value2;
    }

    public double watttokwatt(double value) {
        UnitConverter kwatt = SI.WATT.getConverterTo(AdditionalUnits.KWATT);
        double value1 = kwatt.convert(value);
        return value1;
    }

    public double kwatttowatt(double value) {
        UnitConverter watt = AdditionalUnits.KWATT.getConverterTo(AdditionalUnits.WATT);
        double value1 = watt.convert(value);
        return value1;
    }

    public double kwatttoco2(double value) {
        return value * 0.09;
    }

}
