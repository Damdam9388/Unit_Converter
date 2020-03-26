package fr.houseofcode.unitconverter.entity.datarepository.history;

import fr.houseofcode.unitconverter.entity.datamodel.history.History;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<History,Long> {
}
