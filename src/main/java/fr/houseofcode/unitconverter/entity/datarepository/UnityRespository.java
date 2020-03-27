package fr.houseofcode.unitconverter.entity.datarepository;

import fr.houseofcode.unitconverter.entity.SymbolOnly;
import fr.houseofcode.unitconverter.entity.datamodel.Unity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface UnityRespository extends CrudRepository<Unity, Long> {

    @Query("select new fr.houseofcode.unitconverter.entity.SymbolOnly(u.symbole) from Unity u")
    Collection<SymbolOnly> findAllSummarizedBy();
    List<Unity> findAll();
    Unity findBySymbole(String symbol);

}
