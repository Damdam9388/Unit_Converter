package fr.houseofcode.unitconverter.service;
import fr.houseofcode.unitconverter.dto.InputContent;
import fr.houseofcode.unitconverter.entity.datamodel.Unity;
import fr.houseofcode.unitconverter.entity.datamodel.history.Direction;
import fr.houseofcode.unitconverter.entity.datamodel.history.History;
import fr.houseofcode.unitconverter.entity.datamodel.history.UnityDirectionHistory;
import fr.houseofcode.unitconverter.entity.datarepository.UnityRespository;
import fr.houseofcode.unitconverter.entity.datarepository.history.DirectionRepository;
import fr.houseofcode.unitconverter.entity.datarepository.history.HistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Date;

@Service
public class HistoryService {
    Logger logger = LogManager.getLogger();

    private DirectionRepository directionRepository;
    private UnityRespository unityRespository;
    private HistoryRepository historyRepository;

    private static final String DIR_SOURCE = "source";
    private static final String DIR_TARGET = "target";

    public HistoryService(@Autowired DirectionRepository directionRepository,
                          @Autowired UnityRespository unityRespository,
                          @Autowired HistoryRepository historyRepository) {

        this.directionRepository = directionRepository;
        this.unityRespository = unityRespository;
        this.historyRepository = historyRepository;
    }

    public Iterable<History> getAll() {
        Iterable<History> historyList = historyRepository.findAll();
        return historyList;
    }
    public void save(InputContent data, InputContent res) {
        History history = new History();
        history.setValue(data.getValue());
        history.setResult(res.getValue());
        Date currentDate = new Date();
        history.setCalculationDate(currentDate);

        UnityDirectionHistory unityDirectionHistorySource = new UnityDirectionHistory();
        unityDirectionHistorySource.setDirection(directionRepository.findByType(DIR_SOURCE));
        Unity unitySource = unityRespository.findBySymbole(data.getInputState());
        unityDirectionHistorySource.setUnity(unitySource);
        history.addUnity(unityDirectionHistorySource);

        UnityDirectionHistory unityDirectionHistoryTarget = new UnityDirectionHistory();
        unityDirectionHistoryTarget.setDirection(directionRepository.findByType(DIR_TARGET));
        Unity unityTarget = unityRespository.findBySymbole(res.getInputState());
        unityDirectionHistoryTarget.setUnity(unityTarget);
        history.addUnity(unityDirectionHistoryTarget);

        historyRepository.save(history);

        logger.info("Save success");
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
