package uz.oliymahad.oliymahadquroncourse.payload.request.group;

import lombok.Getter;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.payload.response.user.UserDataResponse;
import uz.oliymahad.oliymahadquroncourse.service.marker.Modifiable;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GroupUpdateRequest implements Modifiable {
    private String name;

    private int membersCount;

    private Integer groupStatus;

    private Date startDate;

    private Long courseId;

    private Integer gender;

    private List<UserDataResponse> user;
}
