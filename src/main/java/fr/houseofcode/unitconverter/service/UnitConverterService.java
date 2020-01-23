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
        return m2.convert(value);
    }

    public double hectareToMeter(double value) {
        UnitConverter ha = AdditionalUnits.HECTARE.getConverterTo(SI.SQUARE_METRE);
        return ha.convert(value);
    }

    public double watttokwatt(double value) {
        UnitConverter kwatt = SI.WATT.getConverterTo(AdditionalUnits.KWATT);
        return kwatt.convert(value);
    }

    public double kwatttowatt(double value) {
        UnitConverter watt = AdditionalUnits.KWATT.getConverterTo(AdditionalUnits.WATT);
        return watt.convert(value);
    }

    public double kwatttoco2(double value) {
        return value * 0.09;
    }
    public double co2ToKwatt(double value) {return value / 0.09; }

}
