package fr.houseofcode.unitconverter.entity.datamodel.history;

import fr.houseofcode.unitconverter.entity.datamodel.Unity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UnityDirectionHistory implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    Unity unity;

    @Id
    @ManyToOne
    @JoinColumn
    Direction direction;

    @Id
    @ManyToOne
    @JoinColumn
    History history;
}
