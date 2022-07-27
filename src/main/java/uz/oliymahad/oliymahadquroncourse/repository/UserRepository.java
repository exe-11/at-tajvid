package uz.oliymahad.oliymahadquroncourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.entity.enums.UserStatus;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = ?1 and u.userStatus <> ?2")
    Optional<User> findByEmailAndStatusIsNot(String email, UserStatus userStatus);

    @Query("select u from User u where u.email = ?1 or u.phoneNumber = ?1 and u.userStatus <> -1")
    Optional<User> findByEmailOrPhoneNumber(String emailOrPhoneNumber);

    @Query("select u from User u where u.id = ?1 and u.userStatus <> ?2")
    Optional<User> findUserByIdAndStatus(Long id, int status);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.phoneNumber = ?1")
    Optional<User> findByPhoneNumber(String phoneNumber);

}
