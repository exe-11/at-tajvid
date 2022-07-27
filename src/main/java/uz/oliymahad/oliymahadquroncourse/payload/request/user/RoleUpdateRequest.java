package uz.oliymahad.oliymahadquroncourse.payload.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUpdateRequest {
    private Long id;

    private int role;
}
