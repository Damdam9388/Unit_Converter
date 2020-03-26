package fr.houseofcode.unitconverter.entity.datamodel.history;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  Date calculationDate;
    private  Double value;
    private  Double result;

}
