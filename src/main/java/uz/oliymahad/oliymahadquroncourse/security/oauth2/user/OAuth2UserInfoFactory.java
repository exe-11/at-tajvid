package uz.oliymahad.oliymahadquroncourse.security.oauth2.user;

import uz.oliymahad.oliymahadquroncourse.entity.enums.AuthProvider;
import uz.oliymahad.oliymahadquroncourse.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw OAuth2AuthenticationProcessingException.notSupported(registrationId);
        }
    }
}
