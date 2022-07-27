package uz.oliymahad.oliymahadquroncourse.exception;

public class AuthorizationRequiredException extends APIException {
    private static final String DEFAULT_MESSAGE_ENG = "Authorization required to do specific action";


    public AuthorizationRequiredException(String message) {
        super(message);
    }

    public AuthorizationRequiredException() {
        super(DEFAULT_MESSAGE_ENG);
    }
}
