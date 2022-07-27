package uz.oliymahad.oliymahadquroncourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.oliymahadquroncourse.payload.request.user.UserSigningRequest;
import uz.oliymahad.oliymahadquroncourse.service.AuthService;

import javax.validation.Valid;

import static uz.oliymahad.oliymahadquroncourse.controller.core.ControllerUtils.AUTH_URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH_URI)
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody UserSigningRequest userSigningRequest) {
        return ResponseEntity.ok(authService.login(userSigningRequest));
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody UserSigningRequest userSigningRequest) {
        return ResponseEntity.ok(authService.register(userSigningRequest));
    }

    @GetMapping("/email-confirmation")
    public ResponseEntity<?> verifyEmail(
            @RequestParam(name = "token") String token,
            @RequestParam(name = "registration-id") Long registrationId
    ){
        return ResponseEntity.ok(authService.verifyEmail(token,registrationId));
    }

    @GetMapping("/email-resend")
    public ResponseEntity<?> resendEmailVerificationToken(
        @RequestParam(name = "token") String token
    ){
        return ResponseEntity.ok(authService.resendEmailVerifier(token));
    }

    /*
    @PostMapping(value = "access/token-from/refresh",
            consumes = {MediaType.TEXT_PLAIN_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> getAccessToken(@RequestBody String refreshToken) {
        return ResponseEntity.ok(authService.getAccessToken(refreshToken));
    }
    */

}
