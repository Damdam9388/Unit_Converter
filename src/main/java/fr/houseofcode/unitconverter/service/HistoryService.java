package fr.houseofcode.unitconverter.service;
import fr.houseofcode.unitconverter.dto.InputContent;
import fr.houseofcode.unitconverter.entity.datamodel.Unity;
import fr.houseofcode.unitconverter.entity.datamodel.history.Direction;
import fr.houseofcode.unitconverter.entity.datamodel.history.History;
import fr.houseofcode.unitconverter.entity.datamodel.history.UnityDirectionHistory;
import fr.houseofcode.unitconverter.entity.datarepository.UnityRespository;
import fr.houseofcode.unitconverter.entity.datarepository.history.DirectionRepository;
import fr.houseofcode.unitconverter.entity.datarepository.history.UnityDirectionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HistoryService {
    private UnityDirectionHistoryRepository unityDirectionHistoryRepository;
    private DirectionRepository directionRepository;
    private UnityRespository unityRespository;

    private static final String DIR_SOURCE = "source";
    private static final String DIR_TARGET = "target";

    public HistoryService(@Autowired UnityDirectionHistoryRepository unityDirectionHistoryRepository,
                          @Autowired DirectionRepository directionRepository,
                          @Autowired UnityRespository unityRespository) {
        this.unityDirectionHistoryRepository = unityDirectionHistoryRepository;
        this.directionRepository = directionRepository;
        this.unityRespository = unityRespository;
    }

    public Iterable<UnityDirectionHistory> getAll() {
        Iterable<UnityDirectionHistory> historyList = unityDirectionHistoryRepository.findAll();
        return historyList;
    }
    public void save(InputContent data, InputContent res) {
        History history = new History();
        history.setValue(data.getValue());
        history.setResult(res.getValue());
        Date currentDate = new Date();
        history.setCalculationDate(currentDate);

        UnityDirectionHistory unityDirectionHistorySource = new UnityDirectionHistory();
        unityDirectionHistorySource.setHistory(history);
        unityDirectionHistorySource.setDirection(directionRepository.findByType(DIR_SOURCE));
        Unity unitySource = unityRespository.findBySymbole(data.getInputState());
        unityDirectionHistorySource.setUnity(unitySource);

        UnityDirectionHistory unityDirectionHistoryTarget = new UnityDirectionHistory();
        unityDirectionHistoryTarget.setHistory(history);
        unityDirectionHistoryTarget.setDirection(directionRepository.findByType(DIR_TARGET));
        Unity unityTarget = unityRespository.findBySymbole(res.getInputState());
        unityDirectionHistoryTarget.setUnity(unityTarget);

        unityDirectionHistoryRepository.save(unityDirectionHistorySource);
        unityDirectionHistoryRepository.save(unityDirectionHistoryTarget);
    }
    public void getLastTen() {
        //TODO get last ten result
    }

    public void init() {
        if(directionRepository.findByType(DIR_SOURCE) == null) {
            Direction directionSrc = new Direction();
            directionSrc.setType(DIR_SOURCE);
            directionRepository.save(directionSrc);
        }
        if (directionRepository.findByType(DIR_TARGET)== null) {
            Direction directionTarget = new Direction();
            directionTarget.setType(DIR_TARGET);
            directionRepository.save(directionTarget);
        }
    }
}
