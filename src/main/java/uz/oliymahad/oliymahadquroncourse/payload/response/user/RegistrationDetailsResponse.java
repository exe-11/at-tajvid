package uz.oliymahad.oliymahadquroncourse.payload.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDetailsResponse {

    private long id;

    private long userId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String gender;

    private String passport;

}
