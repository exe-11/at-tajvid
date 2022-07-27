package uz.oliymahad.oliymahadquroncourse.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.oliymahad.oliymahadquroncourse.exception.*;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;

import javax.persistence.PersistenceException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<?> handlePersistenceException(PersistenceException ex, WebRequest webRequest){
        return ResponseEntity
                .status(CONFLICT)
                .body(APIResponse.error(ex.getMessage(), CONFLICT));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, WebRequest webRequest){
        return ResponseEntity
                .status(NOT_FOUND)
                .body(APIResponse.error(ex.getMessage(), NOT_FOUND));
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<?> handleJwtValidationException(JwtValidationException ex, WebRequest webRequest){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(APIResponse.error(ex.getMessage(), UNAUTHORIZED));
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(DataNotFoundException ex, WebRequest webRequest){
        return ResponseEntity
                .status(NOT_FOUND)
                .body(APIResponse.error(ex.getMessage(), NOT_FOUND));
    }

    @ExceptionHandler(AuthorizationRequiredException.class)
    public ResponseEntity<?> handleAuthorizationException(AuthorizationRequiredException ex, WebRequest webRequest){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(APIResponse.error(ex.getMessage(), UNAUTHORIZED));
    }

    @ExceptionHandler(OAuth2AuthenticationProcessingException.class)
    public ResponseEntity<?> handleOAuth2Exception(OAuth2AuthenticationProcessingException ex, WebRequest webRequest){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(APIResponse.error(ex.getMessage(), UNAUTHORIZED));
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException ex, WebRequest webRequest) {
        return ResponseEntity
                .status(EXPECTATION_FAILED)
                .body(APIResponse.error(ex.getMessage(), EXPECTATION_FAILED));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException ex, WebRequest webRequest) {
        return ResponseEntity
                .status(EXPECTATION_FAILED)
                .body(APIResponse.error(ex.getMessage(), EXPECTATION_FAILED));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest) {
        return ResponseEntity
                .status(EXPECTATION_FAILED)
                .body(APIResponse.error(ex.getMessage(), EXPECTATION_FAILED));
    }
}
