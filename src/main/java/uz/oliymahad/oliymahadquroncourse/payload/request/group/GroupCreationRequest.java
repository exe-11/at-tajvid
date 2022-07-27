package uz.oliymahad.oliymahadquroncourse.payload.request.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.service.marker.Creationable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupCreationRequest implements Creationable{

    @NotBlank
    private String name;

    private int membersCount;

    @NotNull
    private Integer groupStatus;

    @NotNull
    private Date startDate;

    @NotNull
    private Long courseId;

    @NotNull
    private Integer gender;

}
