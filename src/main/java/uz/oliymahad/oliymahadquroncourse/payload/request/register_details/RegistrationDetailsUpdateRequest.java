package uz.oliymahad.oliymahadquroncourse.payload.request.register_details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.service.marker.Modifiable;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationDetailsUpdateRequest implements Modifiable {

    private String firstName;

    private String lastName;

    private String middleName;

    private String passport;

    private String email;

    private String gender;

    private Date birthDate;
}
