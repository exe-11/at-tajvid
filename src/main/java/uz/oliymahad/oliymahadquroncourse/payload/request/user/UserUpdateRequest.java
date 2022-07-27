package uz.oliymahad.oliymahadquroncourse.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.service.marker.Modifiable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest implements Modifiable {

    private String phoneNumber;

    private String email;

    private String password;

    private String imageUrl;

}
