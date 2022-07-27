package uz.oliymahad.oliymahadquroncourse.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.Course;
import uz.oliymahad.oliymahadquroncourse.entity.Queue;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.entity.enums.QueueStatus;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;
import uz.oliymahad.oliymahadquroncourse.payload.request.queue.QueueCreationRequest;
import uz.oliymahad.oliymahadquroncourse.payload.request.queue.QueueUpdateRequest;
import uz.oliymahad.oliymahadquroncourse.payload.response.queue.QueueResponse;
import uz.oliymahad.oliymahadquroncourse.repository.CourseRepository;
import uz.oliymahad.oliymahadquroncourse.repository.QueueRepository;
import uz.oliymahad.oliymahadquroncourse.repository.UserRepository;
import uz.oliymahad.oliymahadquroncourse.service.core.CRUDService;
import uz.oliymahad.oliymahadquroncourse.service.core.PageProvider;
import uz.oliymahad.oliymahadquroncourse.service.core.ResourcePage;

import javax.persistence.PersistenceException;
import java.util.List;

import static uz.oliymahad.oliymahadquroncourse.service.CourseService.COURSE;
import static uz.oliymahad.oliymahadquroncourse.service.UserService.USER;

@Service
@RequiredArgsConstructor
public class QueueService implements CRUDService<
        Long,
        APIResponse,
        QueueCreationRequest,
        QueueUpdateRequest>, PageProvider<APIResponse>
{

    private static final Logger log = LoggerFactory.getLogger(QueueService.class);

    public static final String QUEUE = "Queue";

    private final QueueRepository queueRepository;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;



    @Override
    public APIResponse create(QueueCreationRequest request) {
        final User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> DataNotFoundException.of(USER, request.getUserId())
        );
        final Course course = courseRepository.findById(request.getCourseId()).orElseThrow(
                () -> DataNotFoundException.of(COURSE, request.getCourseId())
        );
        final Queue queue = queueRepository.save(new Queue(user, course));
        return APIResponse.success(modelMapper.map(queue, QueueResponse.class));
    }

    @Override
    public APIResponse get(Long id) {
        final Queue queue = queueRepository.findById(id).orElseThrow(
                () -> DataNotFoundException.of(QUEUE, id)
        );

        return APIResponse.success(modelMapper.map(queue, QueueResponse.class));
    }

    @Override
    public APIResponse modify(Long id, QueueUpdateRequest request) {
        final Queue queue = queueRepository.findById(id).orElseThrow(
                () -> DataNotFoundException.of(QUEUE, id)
        );
        if (request.getCourseId() != null) {
            final Course course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> DataNotFoundException.of(COURSE, id));
            queue.setCourse(course);
        }
        if (request.getUserId() != null){
            final User user = userRepository.findById(request.getCourseId()).orElseThrow(() -> DataNotFoundException.of(USER, request.getUserId()));
            queue.setUser(user);
        }
        queue.setQueueStatus(QueueStatus.getQueueStatus(request.getQueueStatus()));
        try {
            queueRepository.save(queue);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new PersistenceException(exception);
        }
        return APIResponse.success(modelMapper.map(queue, QueueResponse.class));
    }

    @Override
    public APIResponse delete(Long id) {
        final Queue queue = queueRepository.findById(id).orElseThrow(() -> DataNotFoundException.of(QUEUE, id));
        return APIResponse.success(modelMapper.map(queue, QueueResponse.class));
    }

    public APIResponse updateQueueState(Long id, Integer queueStatusValue){
        final Queue queue = queueRepository.findById(id).orElseThrow(() -> DataNotFoundException.of(QUEUE, id));
        queue.setQueueStatus(QueueStatus.getQueueStatus(queueStatusValue));
        try {
            queueRepository.save(queue);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new PersistenceException(exception);
        }
        return APIResponse.success(modelMapper.map(queue, QueueResponse.class));
    }

    public APIResponse rejectQueue(Long queueId){
        final Queue queue = queueRepository.findById(queueId).orElseThrow(() -> DataNotFoundException.of(QUEUE, queueId));
        queue.setQueueStatus(QueueStatus.REJECTED);
        try {
            queueRepository.save(queue);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new PersistenceException(exception);
        }
        return APIResponse.success(modelMapper.map(queue, QueueResponse.class));
    }

    public APIResponse cancelQueue(Long queueId){
        final Queue queue = queueRepository.findById(queueId).orElseThrow(() -> DataNotFoundException.of(QUEUE, queueId));
        queue.setQueueStatus(QueueStatus.CANCELED);
        try {
            queueRepository.save(queue);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new PersistenceException(exception);
        }
        return APIResponse.success(modelMapper.map(queue, QueueResponse.class));
    }

    public APIResponse getUserDetailsFromQueue(Long queueId){
        final Queue queue = queueRepository.findById(queueId).orElseThrow(() -> DataNotFoundException.of(QUEUE, queueId));
        return APIResponse.success(queue.getUser().getRegistrationDetails());
    };

    @Override
    public APIResponse pageOf(Pageable pageable) {
        final Page<Queue> page = queueRepository.findAll(pageable);
        final ResourcePage<QueueResponse> response = new ResourcePage<>();
        modelMapper.map(page,response);
        response.setContent(List.of(modelMapper.map(page.getContent(), QueueResponse[].class)));
        return APIResponse.success(response);
    }


}
