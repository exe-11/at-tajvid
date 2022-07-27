package uz.oliymahad.oliymahadquroncourse.payload.request.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.service.marker.Creationable;
import uz.oliymahad.oliymahadquroncourse.service.marker.Modifiable;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseRequest implements Creationable, Modifiable {

    @Column(unique = true)
    private String name;

    @NotBlank
    private String description;

    private double price;

    private float duration;
}
