package fr.houseofcode.unitconverter.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class InputContent {
    private Double value;
    private String inputState;
    private String outputState;
}
