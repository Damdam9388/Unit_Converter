package fr.houseofcode.unitconverter.entity.datamodel.history;

import fr.houseofcode.unitconverter.entity.datamodel.Unity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class UnityDirectionHistoryId implements Serializable {
    private Unity unity;

    private Direction direction;

    private History history;

    public UnityDirectionHistoryId(Unity unity, Direction direction, History history) {

        this.unity = unity;
        this.direction = direction;
        this.history = history;
    }
}
