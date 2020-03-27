package fr.houseofcode.unitconverter.entity.datamodel.history;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  Date calculationDate;
    private  Double value;
    private  Double result;

    @OneToMany(mappedBy="history", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<UnityDirectionHistory> unityDirectionHistory;

    public History(){
        unityDirectionHistory = new ArrayList<>();
    }
    public void addUnity(UnityDirectionHistory unityDirectionHistory){
        this.unityDirectionHistory.add(unityDirectionHistory);
        unityDirectionHistory.setHistory(this);
    }
}
