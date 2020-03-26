package fr.houseofcode.unitconverter.entity.datamodel;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Source {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String link;
    @OneToMany
    private List<Unity> unities;


}
