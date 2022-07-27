package uz.oliymahad.oliymahadquroncourse.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class APIException extends RuntimeException {

    public APIException(String message) {
        super(message);
    }
}
