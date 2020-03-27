package fr.houseofcode.unitconverter.entity.datamodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Unity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String symbole;
    private String definition;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Source source;
}
