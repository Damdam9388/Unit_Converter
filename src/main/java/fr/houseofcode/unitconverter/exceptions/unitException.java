package fr.houseofcode.unitconverter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class unitException extends RuntimeException {

    public unitException(String s) {
        super(s);
    }
}
