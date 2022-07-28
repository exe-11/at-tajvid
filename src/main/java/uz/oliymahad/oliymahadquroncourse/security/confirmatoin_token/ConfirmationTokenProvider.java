package uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token;


import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationTokenProvider {

    private final static String CONFIRMATION_TOKEN = "Confirmation-Token";

    private final static long   EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    private final ConfirmationTokenRepository repository;

    public ConfirmationTokenProvider(ConfirmationTokenRepository repository) {
        this.repository = repository;
    }

    public ConfirmationToken create(final User user){
        final Date creationDate = new Date();
        final Date expiryDate = new Date(creationDate.getTime() + EXPIRATION_TIME);
        return repository.save(new ConfirmationToken(creationDate, expiryDate, user));
    }

    public ConfirmationToken update(final ConfirmationToken confirmationToken){
        final Date creationDate = new Date();
        final Date expiryDate = new Date(creationDate.getTime() + EXPIRATION_TIME);
        confirmationToken.setToken(UUID.randomUUID().toString());
        confirmationToken.setCreationDate(creationDate);
        confirmationToken.setExpiryDate(expiryDate);
        return confirmationToken;
    }

    public Optional<ConfirmationToken> get(final String token){
        return repository.findByToken(token);
    }

    public ConfirmationToken confirmToken(final String token){
        final ConfirmationToken confirmationToken = get(token).orElseThrow(() -> DataNotFoundException.of(CONFIRMATION_TOKEN, token));

        if(confirmationToken.getExpiryDate().before(new Date())){
            throw new IllegalStateException(CONFIRMATION_TOKEN + " is already expired");
        }

        if (confirmationToken.getConfirmationDate() != null) {
            throw new IllegalStateException("Email is already verified");
        }

        confirmationToken.setConfirmationDate(new Date());
        repository.save(confirmationToken);
        return confirmationToken;
    }

}
