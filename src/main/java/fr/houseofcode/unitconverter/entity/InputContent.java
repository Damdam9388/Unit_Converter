package fr.houseofcode.unitconverter.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
public class InputContent {
    @NotNull
    private Double value;
    @NotNull
    private String inputState;
    @NotNull
    private String outputState;
}
