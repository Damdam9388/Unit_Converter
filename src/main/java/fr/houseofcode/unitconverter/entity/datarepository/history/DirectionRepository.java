package fr.houseofcode.unitconverter.entity.datarepository.history;

import fr.houseofcode.unitconverter.entity.datamodel.history.Direction;
import org.springframework.data.repository.CrudRepository;

public interface DirectionRepository extends CrudRepository<Direction, Long> {
    public Direction findByType(String type);
}
