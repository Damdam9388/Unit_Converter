package fr.houseofcode.unitconverter.entity.datarepository.history;

import fr.houseofcode.unitconverter.entity.datamodel.history.UnityDirectionHistory;
import fr.houseofcode.unitconverter.entity.datamodel.history.UnityDirectionHistoryId;
import org.springframework.data.repository.CrudRepository;

public interface UnityDirectionHistoryRepository extends CrudRepository<UnityDirectionHistory, UnityDirectionHistoryId> {
}
