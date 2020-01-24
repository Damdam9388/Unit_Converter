package fr.houseofcode.unitconverter.service;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import fr.houseofcode.unitconverter.entity.InputContent;
import org.springframework.stereotype.Service;

import fr.houseofcode.unitconverter.entity.AdditionalUnits;
import si.uom.SI;

/**
 * @author dte
 *
 */
@Service
public class UnitConverterService {

    private Double result = null;

    public Double convertKwattCo2(double value, InputContent data){
        if(data.getInputState().equals("Kw") && data.getOutputState().equals("Co2")){
            result = convert(value, SI.WATT,AdditionalUnits.KWATT);
            result = result * 0.09;
        } else if(data.getInputState().equals("Co2") && data.getOutputState().equals("Kw")){
            UnitConverter watt = AdditionalUnits.KWATT.getConverterTo(AdditionalUnits.WATT);
            result = watt.convert(value);
            result = result / 0.09;
        }
        return result;
    }


    public <Q extends Quantity<Q>> Double convert(double value, Unit<Q> source, Unit<Q> dest){
        UnitConverter unit = source.getConverterTo(dest);
        result = unit.convert(value);
        return result;
    }


}
