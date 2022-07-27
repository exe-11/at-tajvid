package uz.oliymahad.oliymahadquroncourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.oliymahad.oliymahadquroncourse.entity.RegistrationDetails;
import uz.oliymahad.oliymahadquroncourse.entity.User;

import java.util.Optional;


public interface RegistrationDetailsRepository extends JpaRepository<RegistrationDetails, Long> {

    Optional<RegistrationDetails> findByUser(User user);

    Optional<RegistrationDetails> findByUserId(Long id);

}
