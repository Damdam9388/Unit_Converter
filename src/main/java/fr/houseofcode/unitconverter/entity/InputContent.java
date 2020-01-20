package fr.houseofcode.unitconverter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.measure.UnitConverter;

@Getter
@Setter
@NoArgsConstructor
public class InputContent {
    private Double value;
    private String inputState;
    private String outputState;
}
