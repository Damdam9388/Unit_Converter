package fr.houseofcode.unitconverter.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
public class SymbolOnly{
    private String symbole;

    @Override
    public String toString() {
        return this.symbole;
    }
}

//public interface SymbolOnly {
//
//    @Value("#{target.symbole}")
//    String getSymbole();
//    private String symbole;
//
//    @Override
//    public String toString() {
//        return this.symbole;
//    }
//}
