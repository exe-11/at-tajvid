package uz.oliymahad.oliymahadquroncourse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uz.oliymahad.oliymahadquroncourse.audit.Auditable;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Gender;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_register_details",
        indexes = {
                @Index(name = "firstName", columnList = "firstName DESC"),
                @Index(name = "middleName", columnList = "middleName DESC"),
                @Index(name = "lastName", columnList = "lastName DESC"),
                @Index(name = "birthDate", columnList = "birthDate DESC"),
        }
)
public class RegistrationDetails extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    //    @Column(nullable = false, length = 50)
    private String firstName;

    //    @Column(nullable = false, length = 50)
    private String middleName;

    //    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    //    @Column(nullable = false, unique = true)
    private String passport;

    //    @Column(nullable = false)
    private Date birthDate;

}
