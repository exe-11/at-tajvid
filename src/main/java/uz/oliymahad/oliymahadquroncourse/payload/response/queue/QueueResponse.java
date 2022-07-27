package uz.oliymahad.oliymahadquroncourse.payload.response.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Gender;
import uz.oliymahad.oliymahadquroncourse.entity.enums.QueueStatus;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QueueResponse {

    private Long id;

    private String courseName;

    private Long userId;

    private String phoneNumber;

    private String email;

    private String firstName;

    private String lastName;

    private String middleName;

    private Date birthDate;

    private String passportSerial;

    private LocalDateTime appliedDate;

    private QueueStatus queueStatus;

    private Gender gender;
}
