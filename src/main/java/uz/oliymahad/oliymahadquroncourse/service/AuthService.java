package uz.oliymahad.oliymahadquroncourse.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.annotation.phone_num_constraint.RegistrationValidator;
import uz.oliymahad.oliymahadquroncourse.email.EmailService;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.entity.enums.UserStatus;
import uz.oliymahad.oliymahadquroncourse.exception.AuthorizationRequiredException;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.exception.JwtValidationException;
import uz.oliymahad.oliymahadquroncourse.exception.UserPendingException;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;
import uz.oliymahad.oliymahadquroncourse.payload.request.user.UserSigningRequest;
import uz.oliymahad.oliymahadquroncourse.payload.response.JwtTokenResponse;
import uz.oliymahad.oliymahadquroncourse.payload.response.user.UserDataResponse;
import uz.oliymahad.oliymahadquroncourse.repository.UserRepository;
import uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token.ConfirmationToken;
import uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token.ConfirmationTokenProvider;
import uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token.ConfirmationTokenRepository;
import uz.oliymahad.oliymahadquroncourse.security.jwt.JWTokenProvider;

import javax.persistence.PersistenceException;
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

    private final ModelMapper modelMapper;


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

    private APIResponse registerByEmail(final UserSigningRequest request) {

        try {
            processingEmailRegistration(request.getPhoneNumberOrEmail());

            User user = userRepository.save(User.ofEmail(
                    request.getPhoneNumberOrEmail(),
                    passwordEncoder.encode(request.getPassword())
            ));
            emailService.sendConfirmationMessage(user.getEmail(), user);
            return APIResponse.success(HttpStatus.OK);

        } catch (UserPendingException exception) {

            final ConfirmationToken confirmationToken = confirmationTokenRepository.findByUserEmail(request.getPhoneNumberOrEmail());
            confirmationTokenProvider.update(confirmationToken);
            confirmationTokenRepository.save(confirmationToken);

            emailService.sendConfirmationMessage(request.getPhoneNumberOrEmail(), confirmationToken.getUser());

        } catch (Exception exception) {

            logger.error(exception.getLocalizedMessage());
            throw new RuntimeException(exception.getMessage());
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
            throw new RuntimeException(exception.getMessage());
        }
    }


    private APIResponse registerByPhoneNumber(UserSigningRequest request) {
        try{
            processingPhoneNumberRegistration(request.getPhoneNumberOrEmail());

            final User user = userRepository.save(User.ofPhoneNumber(
                    request.getPhoneNumberOrEmail(),
                    passwordEncoder.encode(request.getPassword())
            ));
            user.setUserStatus(UserStatus.ACTIVE);
            final JwtTokenResponse tokenResponse = modelMapper.map(user, JwtTokenResponse.class);
            tokenResponse.setAccessToken(jwTokenProvider.generateAccessToken(user));
            return APIResponse.success(tokenResponse);
        }catch (UserPendingException exception){
//            TODO - SMS verification must be added
        }
        return APIResponse.error(HttpStatus.NOT_EXTENDED);
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

    private void processingEmailRegistration(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        checkUserState(optionalUser, email);
    }

    private void processingPhoneNumberRegistration(String phoneNumber) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        checkUserState(optionalUser, phoneNumber);
    }

    private void checkUserState(Optional<User> optionalUser, String resource){
        if (optionalUser.isPresent() && optionalUser.get().getUserStatus().equals(UserStatus.ACTIVE)) {
            throw new PersistenceException("User already registered with " + resource);
        } else if (optionalUser.isPresent() && optionalUser.get().getUserStatus().equals(UserStatus.BLOCKED)) {
            throw new AuthorizationRequiredException("This account is blocked");
        } else if (optionalUser.isPresent() && optionalUser.get().getUserStatus().equals(UserStatus.PENDING)) {
            throw new UserPendingException();
        }
    }

    public UserDataResponse getUser() {
        final User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(principal, UserDataResponse.class);
    }

}
