package uz.oliymahad.oliymahadquroncourse.payload.response.register_details;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegistrationDetailResponse {

    private long id;

    private long userId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String gender;

    private String passport;

    private Date birthDate;

}