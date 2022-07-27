package uz.oliymahad.oliymahadquroncourse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oliymahad.oliymahadquroncourse.controller.core.AbstractCRUDController;
import uz.oliymahad.oliymahadquroncourse.controller.core.PageController;
import uz.oliymahad.oliymahadquroncourse.payload.request.course.CourseRequest;
import uz.oliymahad.oliymahadquroncourse.service.CourseService;

import static uz.oliymahad.oliymahadquroncourse.controller.core.ControllerUtils.COURSES_URI;


@RestController
@RequestMapping(COURSES_URI)
public class CourseController extends AbstractCRUDController<
        CourseService,
        Long,
        CourseRequest,
        CourseRequest> implements PageController<CourseService> {


    protected CourseController(CourseService service) {
        super(service);
    }
}