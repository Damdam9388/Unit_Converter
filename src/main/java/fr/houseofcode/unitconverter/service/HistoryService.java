package fr.houseofcode.unitconverter.service;
import fr.houseofcode.unitconverter.entity.datamodel.history.History;
import fr.houseofcode.unitconverter.entity.datarepository.history.HistoryRepository;
import fr.houseofcode.unitconverter.entity.datarepository.history.UnityDirectionHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    private UnityDirectionHistoryRepository unityDirectionHistoryRepository;
    private HistoryRepository historyRepository;

    public HistoryService(UnityDirectionHistoryRepository unityDirectionHistoryRepository) {
        this.unityDirectionHistoryRepository = unityDirectionHistoryRepository;
    }

    public Iterable<History> getAll() {
        Iterable<History> historyList = historyRepository.findAll();
        return historyList;
    }
    public void save() {
        //unityDirectionHistoryRepository.save();
    }
    public void getLastTen() {
        //TODO get last ten result
    }

}
