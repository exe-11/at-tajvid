package uz.oliymahad.oliymahadquroncourse.payload.response;

import lombok.Getter;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.entity.Course;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Gender;
import uz.oliymahad.oliymahadquroncourse.entity.enums.GroupStatus;
import uz.oliymahad.oliymahadquroncourse.payload.Response;
import uz.oliymahad.oliymahadquroncourse.payload.response.course.CourseResponse;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class GroupResponse implements Response {
    private Long id;

    private String name;

    private int membersCount;

    private GroupStatus groupStatus;

    private Date startDate;

    private CourseResponse course;

    private Gender gender;

    private List<User> users;
}
