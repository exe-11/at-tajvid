package uz.oliymahad.oliymahadquroncourse.payload.request.register_details;


import lombok.Getter;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.service.marker.Creationable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class RegistrationDetailsCreationRequest implements Creationable {
    @NotNull
    private Long userId;

    @Size(min = 3,max = 30)
    private String firstName;

    @Size(min = 3,max = 30)
    private String middleName;

    @Size(min = 3,max = 30)
    private String lastName;

    @NotBlank
    private String gender;

    @Size(min = 8, max = 24)
    private String passport;

    @NotBlank
    private Date birthDate;

}
