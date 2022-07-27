package uz.oliymahad.oliymahadquroncourse.payload.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.entity.RegistrationDetails;
import uz.oliymahad.oliymahadquroncourse.entity.enums.AuthProvider;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDataResponse {

    private Long id;

    private String username;

    private String phoneNumber;

    private String email;

    private AuthProvider provider;

    private String imageUrl;

    private int roles;

    private RegistrationDetails registrationDetails;

    private Timestamp createdAt;

}