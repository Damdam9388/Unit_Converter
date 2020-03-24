package fr.houseofcode.unitconverter.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class InputContent {
    @NotNull(message = "la valeur ne peut pas être nulle")
    private Double value;
    @NotEmpty(message = "vous devez rentrer une unité de départ")
    private String inputState;
    @NotEmpty(message = "vous devez renseigner une unité de sortie")
    private String outputState;

    private List<String> errors= new ArrayList<>();
    private  List<String> allInputData;

}
