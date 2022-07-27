package uz.oliymahad.oliymahadquroncourse.exception;

import uz.oliymahad.oliymahadquroncourse.entity.enums.AuthProvider;

public class OAuth2AuthenticationProcessingException extends APIException {

    private static final String WRONG_PROVIDER_ENG = "Please use your %s account to login";

    private static final String ACCOUNT_SUSPENDED_ENG = "Your account suspended";

    private static final String EMAIL_NOT_FOUND_ENG = "Email not found from authentication provider";

    private static final String NOT_SUPPORTED_ENG = "Login with %s is not supported yet";

    public OAuth2AuthenticationProcessingException(String message) {
        super(message);
    }

    public static OAuth2AuthenticationProcessingException notSupported(String registrationId){
        return new OAuth2AuthenticationProcessingException(
                String.format(NOT_SUPPORTED_ENG,registrationId)
        );
    }

    public static OAuth2AuthenticationProcessingException ofProvider(AuthProvider authProvider){
        return new OAuth2AuthenticationProcessingException(String.format(WRONG_PROVIDER_ENG,authProvider.name()));
    }

    public static OAuth2AuthenticationProcessingException suspended(){
        return new OAuth2AuthenticationProcessingException(ACCOUNT_SUSPENDED_ENG);
    }

    public static OAuth2AuthenticationProcessingException ofEmail(){
        return new OAuth2AuthenticationProcessingException(EMAIL_NOT_FOUND_ENG);
    }


}
