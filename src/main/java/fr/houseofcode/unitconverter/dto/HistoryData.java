package fr.houseofcode.unitconverter.dto;

import fr.houseofcode.unitconverter.entity.datamodel.history.History;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@NoArgsConstructor
public class HistoryData {
    private Iterable<History> historyList;
}
