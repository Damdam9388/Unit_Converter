package fr.houseofcode.unitconverter.entity.datamodel.history;

import fr.houseofcode.unitconverter.entity.datamodel.Unity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@IdClass(UnityDirectionHistoryId.class)
public class UnityDirectionHistory implements Serializable {
    @Id
    @ManyToOne
    Unity unity;

    @Id
    @ManyToOne
    Direction direction;

    @Id
    @ManyToOne
    History history;
}
