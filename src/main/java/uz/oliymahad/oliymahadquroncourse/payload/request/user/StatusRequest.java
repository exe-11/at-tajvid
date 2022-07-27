package uz.oliymahad.oliymahadquroncourse.payload.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StatusRequest {

    @NotNull
    private Long userId;

    @NotNull
    private String status;
}
