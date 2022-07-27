package uz.oliymahad.oliymahadquroncourse.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.Course;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;
import uz.oliymahad.oliymahadquroncourse.payload.request.course.CourseRequest;
import uz.oliymahad.oliymahadquroncourse.payload.response.course.CourseResponse;
import uz.oliymahad.oliymahadquroncourse.repository.CourseRepository;
import uz.oliymahad.oliymahadquroncourse.service.core.CRUDService;
import uz.oliymahad.oliymahadquroncourse.service.core.PageProvider;
import uz.oliymahad.oliymahadquroncourse.service.core.ResourcePage;

import javax.persistence.PersistenceException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CourseService implements CRUDService<Long, APIResponse, CourseRequest, CourseRequest> , PageProvider<APIResponse> {

    public final static String COURSE = "Course";

    private final static Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;


    @Override
    public APIResponse create(CourseRequest courseRequest) {
        final Course course = modelMapper.map(courseRequest, Course.class);
        try {
            courseRepository.save(course);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new PersistenceException(exception);
        }

        return APIResponse.success(modelMapper.map(course,CourseResponse.class));
    }

    @Override
    public APIResponse get(Long id) {
        final Course course = courseRepository.findById(id).orElseThrow(
                () -> DataNotFoundException.of(COURSE, id)
        );
        return APIResponse.success(modelMapper.map(course, CourseResponse.class));
    }

    @Override
    public APIResponse modify(Long id, CourseRequest courseRequest) {
        final Course course = courseRepository.findById(id).orElseThrow(
                () -> DataNotFoundException.of(COURSE, id)
        );
        modelMapper.map(course, courseRequest);

        try{
            courseRepository.save(course);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new PersistenceException(exception);
        }

        return APIResponse.success(modelMapper.map(course, CourseResponse.class));
    }

    @Override
    public APIResponse delete(Long id) {
        final Course course = courseRepository.findById(id).orElseThrow(
                () -> DataNotFoundException.of(COURSE, id)
        );
        courseRepository.delete(course);
        return APIResponse.success(HttpStatus.OK.name());
    }

    @Override
    public APIResponse pageOf(Pageable pageable) {
        final Page<Course> page = courseRepository.findAll(pageable);
        ResourcePage<CourseResponse> resource = new ResourcePage<>();
        modelMapper.map(page, resource);
        resource.setContent(List.of(modelMapper.map(page.getContent(),CourseResponse[].class)));
        return APIResponse.success(resource);
    }
}