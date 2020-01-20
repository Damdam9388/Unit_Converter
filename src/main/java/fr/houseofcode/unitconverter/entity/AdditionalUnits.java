/**
 * 
 */
package fr.houseofcode.unitconverter.entity;

import javax.measure.Unit;
import javax.measure.quantity.Area;

import tec.uom.se.AbstractUnit;
import tec.uom.se.function.RationalConverter;
import tec.uom.se.unit.TransformedUnit;
import tec.uom.se.unit.Units;

/**
 * @author karam.findakly
 *
 */
public class AdditionalUnits extends Units {

    protected AdditionalUnits() {
    }

    private static final AdditionalUnits INSTANCE = new AdditionalUnits();

    @Override
    public String getName() {
        return AdditionalUnits.class.getSimpleName();
    }

    /**
     * Returns the singleton instance of this class.
     *
     * @return the metric system instance.
     */
    public static AdditionalUnits getInstance() {
        return INSTANCE;
    }

    /**
     * An angle unit accepted for use with SI units (standard name
     * <code>ha</code>).
     */
    public static final Unit<Area> HECTARE = new TransformedUnit<Area>(SQUARE_METRE, new RationalConverter(10000, 1));

    public static <U extends Unit<?>> U addUnit(U unit, String name, String symbol) {
        //        if (isLabel) {
        //            SimpleUnitFormat.getInstance().label(unit, text);
        //        }
        if (name != null && unit instanceof AbstractUnit) {
            return Helper.addUnit(INSTANCE.units, unit, name);
        } else {
            INSTANCE.units.add(unit);
        }
        return unit;
    }
}
