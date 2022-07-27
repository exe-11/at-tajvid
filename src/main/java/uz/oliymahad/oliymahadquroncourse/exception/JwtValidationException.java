package uz.oliymahad.oliymahadquroncourse.exception;
public class JwtValidationException extends RuntimeException{
    public JwtValidationException(String message) {
        super(message);
    }
}
