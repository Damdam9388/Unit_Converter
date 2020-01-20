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
}
