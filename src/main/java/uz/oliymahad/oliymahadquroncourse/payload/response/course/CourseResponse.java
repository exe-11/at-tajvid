package uz.oliymahad.oliymahadquroncourse.payload.response.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.payload.Response;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseResponse implements Response {

    private Long id;

    private String name;

    private String description;

    private double price;

    private float duration;
}
