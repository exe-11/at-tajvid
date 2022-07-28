package uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Query(value = "UPDATE confirmation_token SET confirmation_date = :date  WHERE token = :token and expiry_date < now()", nativeQuery = true)
    int updateConfirmationDate(String token);

    @Query("select c from ConfirmationToken c where c.user.email = ?1")
    ConfirmationToken findByUserEmail(String email);

    @Query("select c from ConfirmationToken c where c.user.phoneNumber = ?1")
    ConfirmationToken findByUserPhoneNumber(String phoneNumber);
}
