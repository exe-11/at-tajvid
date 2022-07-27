package uz.oliymahad.oliymahadquroncourse.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.annotation.phone_num_constraint.PhoneNumberOrEmail;
import uz.oliymahad.oliymahadquroncourse.service.marker.Creationable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSigningRequest implements Creationable {

    @PhoneNumberOrEmail
    private String phoneNumberOrEmail;

    @NotBlank
    @Size(min = 4, max = 16)
    private String password;



}
