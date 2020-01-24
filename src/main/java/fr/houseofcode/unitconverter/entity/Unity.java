package fr.houseofcode.unitconverter.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Unity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String symbole;
    private String definition;
    @ManyToOne
    private Source source;

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbole() {
        return symbole;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }


    @Override
    public String toString() {
        return String.format(
                "Unity[id=%d, symbole='%s', definition='%s']",
                id, symbole, definition);
    }


}
