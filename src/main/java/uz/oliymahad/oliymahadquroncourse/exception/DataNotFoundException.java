package uz.oliymahad.oliymahadquroncourse.exception;

public class DataNotFoundException extends APIException {

    private static final String DEFAULT_MESSAGE_ENG = "%s not found with %s";

    public DataNotFoundException(String message) {
        super(message);
    }

    public static DataNotFoundException of(String resource, String parameter) {
        return new DataNotFoundException(String.format(DEFAULT_MESSAGE_ENG, resource, parameter));
    }
    public static DataNotFoundException of(String resource, Long parameter) {
        return new DataNotFoundException(String.format(DEFAULT_MESSAGE_ENG, resource, parameter));
    }
}
