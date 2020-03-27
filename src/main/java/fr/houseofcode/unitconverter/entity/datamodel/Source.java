package fr.houseofcode.unitconverter.entity.datamodel;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Source {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String link;

    @OneToMany(mappedBy = "source")
    private List<Unity> unities;

    public Source() {
        this.unities = new ArrayList<>();
    }

    public void addUnity(Unity unity) {
        this.unities.add(unity);
        unity.setSource(this);
    }


}
