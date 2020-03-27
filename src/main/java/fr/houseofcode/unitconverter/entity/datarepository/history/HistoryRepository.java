package fr.houseofcode.unitconverter.entity.datarepository.history;

import fr.houseofcode.unitconverter.entity.datamodel.history.History;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History,Long> {
    List<History> findAllByOrderByCalculationDateAsc();
    List<History> findFirst10ByOrderByCalculationDateDesc();
}
