package uz.oliymahad.oliymahadquroncourse.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.oliymahad.oliymahadquroncourse.audit.Auditable;
import uz.oliymahad.oliymahadquroncourse.entity.enums.AuthProvider;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Language;
import uz.oliymahad.oliymahadquroncourse.entity.enums.UserStatus;

import javax.persistence.*;


@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

//    @Column(unique = true)
    private String phoneNumber;

    //    @Column(nullable = false)
    private String password;

//    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "int default 1")
    private Integer roles = 1;

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "int default 1")
    private Language language = Language.ENG;

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "int default 1")
    private UserStatus userStatus = UserStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'local'")
    private AuthProvider provider = AuthProvider.local;

    private String providerId;

    private String imageUrl;

    @OneToOne(fetch = FetchType.EAGER)
    private RegistrationDetails registrationDetails;

    private User(String email, UserStatus userStatus, String password) {
        this.email = email;
        this.userStatus = userStatus;
        this.password = password;
    }

    public static User ofEmail(String email, String password) {
        return new User(email, UserStatus.PENDING, password);
    }

    private User(String password, UserStatus userStatus) {
        this.password = password;
        this.userStatus = userStatus;
    }

    public static User ofPhoneNumber(String phoneNumber, String password) {
        User user = new User(password, UserStatus.PENDING);
        user.setPhoneNumber(phoneNumber);
        return user;
    }


}

