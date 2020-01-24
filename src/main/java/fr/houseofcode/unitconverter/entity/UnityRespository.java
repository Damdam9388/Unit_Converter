package fr.houseofcode.unitconverter.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UnityRespository extends CrudRepository<Unity, Long> {

    @Query("select new fr.houseofcode.unitconverter.entity.SymbolOnly(u.symbole) from Unity u")
    Collection<SymbolOnly> findAllSummarizedBy();
    List<Unity> findAll();

}
