package uz.oliymahad.oliymahadquroncourse.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.annotation.phone_num_constraint.RegistrationValidator;
import uz.oliymahad.oliymahadquroncourse.email.EmailService;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.entity.enums.UserStatus;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.exception.JwtValidationException;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;
import uz.oliymahad.oliymahadquroncourse.payload.request.user.UserSigningRequest;
import uz.oliymahad.oliymahadquroncourse.payload.response.JwtTokenResponse;
import uz.oliymahad.oliymahadquroncourse.repository.UserRepository;
import uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token.ConfirmationToken;
import uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token.ConfirmationTokenProvider;
import uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token.ConfirmationTokenRepository;
import uz.oliymahad.oliymahadquroncourse.security.jwt.JWTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    String LOGIN_ERROR = "Email or Password is incorrect";

    String REGISTRATION_ERROR = "This email already signed up. Please log in!";

    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final RegistrationValidator validator;

    private final ConfirmationTokenProvider confirmationTokenProvider;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final JWTokenProvider jwTokenProvider;

    private final PasswordEncoder passwordEncoder;


    public APIResponse login(UserSigningRequest userLoginRequest) {
        Optional<User> optionalUser = userRepository.findByEmailOrPhoneNumber(userLoginRequest.getPhoneNumberOrEmail());
        if (optionalUser.isPresent() &&
                passwordEncoder.matches(userLoginRequest.getPassword(), optionalUser.get().getPassword())) {
            return APIResponse.success(createJwtSuccessResponse(optionalUser.get()));
        }
        throw new JwtValidationException(LOGIN_ERROR);
    }

    public APIResponse register(UserSigningRequest userSigningRequest) {
        if (validator.isEmail(userSigningRequest.getPhoneNumberOrEmail())) {
            return registerByEmail(userSigningRequest);
        }

        if (validator.isPhoneNumber(userSigningRequest.getPhoneNumberOrEmail())) {
            return registerByPhoneNumber(userSigningRequest);
        }
        throw new IllegalStateException();
    }

    private APIResponse registerByEmail(final UserSigningRequest userSigningRequest) {
        try {
            User user = userRepository.save(User.ofEmail(
                    userSigningRequest.getPhoneNumberOrEmail(),
                    passwordEncoder.encode(userSigningRequest.getPassword())
            ));
            emailService.sendConfirmationMessage(user.getEmail(), user);
            return APIResponse.success(HttpStatus.OK);
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage());
//            throw new RuntimeException(exception.getMessage());
        }
        return APIResponse.success(HttpStatus.OK.name());
    }

    public APIResponse verifyEmail(final String token, final Long confirmationId) {
        final ConfirmationToken confirmationToken = confirmationTokenProvider.confirmToken(token);
        final JwtTokenResponse tokenResponse = createJwtSuccessResponse(confirmationToken.getUser());
        confirmationToken.getUser().setUserStatus(UserStatus.ACTIVE);
        tokenResponse.setAccessToken(jwTokenProvider.generateAccessToken(confirmationToken.getUser()));
        confirmationTokenRepository.save(confirmationToken);
        userRepository.save(confirmationToken.getUser());
        return APIResponse.success(tokenResponse);
    }

    public APIResponse resendEmailVerifier(String token) {
        try {
            final ConfirmationToken oldToken = confirmationTokenRepository.findByToken(token).orElseThrow(() -> DataNotFoundException.of("Confirmation-token", token));
            emailService.sendConfirmationMessage(oldToken.getUser().getEmail(), oldToken.getUser());
            confirmationTokenRepository.delete(oldToken);
            return APIResponse.success(HttpStatus.OK);
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage());
//            throw new RuntimeException(exception.getMessage());
        }
        return null;
    }


    private APIResponse registerByPhoneNumber(UserSigningRequest userSigningRequest) {
        return null;
    }


    private JwtTokenResponse createJwtSuccessResponse(User user) {
        return new JwtTokenResponse(
                user.getId(),
                user.getRoles(),
                user.getUsername(),
                user.getLanguage(),
                user.getImageUrl(),
                jwTokenProvider.generateAccessToken(user));
    }

}
