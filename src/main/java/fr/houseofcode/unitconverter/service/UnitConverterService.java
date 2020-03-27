package fr.houseofcode.unitconverter.service;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import fr.houseofcode.unitconverter.dto.InputContent;
import fr.houseofcode.unitconverter.entity.datamodel.Source;
import fr.houseofcode.unitconverter.entity.datamodel.Unity;
import fr.houseofcode.unitconverter.entity.datarepository.UnityRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import si.uom.SI;

/**
 * @author dte
 *
 */
@Service
public class UnitConverterService {
    private UnityRespository unityRespository;

    public UnitConverterService(@Autowired UnityRespository unityRespository) {
        this.unityRespository = unityRespository;
    }

    public Double convertKwattCo2(double value, InputContent data){
        Double result = null;

        if(data.getInputState().equals("kW") && data.getOutputState().equals("Co²")){
            result = convert(value, SI.WATT,AdditionalUnits.KWATT);
            result = result * 0.09;
        } else if(data.getInputState().equals("Co²") && data.getOutputState().equals("kW")){
            UnitConverter watt = AdditionalUnits.KWATT.getConverterTo(AdditionalUnits.WATT);
            result = watt.convert(value);
            result = result / 0.09;
        }
        return result;
    }


    public <Q extends Quantity<Q>> Double convert(double value, Unit<Q> source, Unit<Q> dest){
        Double result = null;

        UnitConverter unit = source.getConverterTo(dest);
        result = unit.convert(value);
        return result;
    }
    public void init() {
        createUnity("m²", "1 carré de 1 * 1 metre", "https://fr.wikipedia.org/wiki/M%C3%A8tre_carr%C3%A9");
        createUnity("ha", "1 carré de 100 * 100 metre", "https://fr.wikipedia.org/wiki/Hectare");
        createUnity("km²", "1 carré de 1 * 1 km","https://fr.wikipedia.org/wiki/Kilom%C3%A8tre_carr%C3%A9");
        createUnity("kW", "1 000 watt","https://fr.wikipedia.org/wiki/Watt");
        createUnity("Co²", "gramme de Co² pour 1m cube d'aire","https://www.greenit.fr/2009/04/24/combien-de-co2-degage-un-1-kwh-electrique/");
    }

    private void createUnity(String symbole, String definition, String sourceLink) {
        Unity unity = new Unity();
        unity.setSymbole(symbole);
        unity.setDefinition(definition);

        Source source = new Source();
        source.setLink(sourceLink);
        source.addUnity(unity);

        unityRespository.save(unity);
    }
}
