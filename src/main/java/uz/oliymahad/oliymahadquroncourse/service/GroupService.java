package uz.oliymahad.oliymahadquroncourse.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.Course;
import uz.oliymahad.oliymahadquroncourse.entity.Group;
import uz.oliymahad.oliymahadquroncourse.entity.Queue;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.entity.enums.GroupStatus;
import uz.oliymahad.oliymahadquroncourse.entity.enums.QueueStatus;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;
import uz.oliymahad.oliymahadquroncourse.payload.request.group.GroupCreationRequest;
import uz.oliymahad.oliymahadquroncourse.payload.request.group.GroupUpdateRequest;
import uz.oliymahad.oliymahadquroncourse.payload.response.GroupResponse;
import uz.oliymahad.oliymahadquroncourse.payload.response.queue.QueueResponse;
import uz.oliymahad.oliymahadquroncourse.payload.response.user.UserDataResponse;
import uz.oliymahad.oliymahadquroncourse.repository.CourseRepository;
import uz.oliymahad.oliymahadquroncourse.repository.GroupRepository;
import uz.oliymahad.oliymahadquroncourse.repository.QueueRepository;
import uz.oliymahad.oliymahadquroncourse.repository.UserRepository;
import uz.oliymahad.oliymahadquroncourse.service.core.CRUDService;
import uz.oliymahad.oliymahadquroncourse.service.core.PageProvider;
import uz.oliymahad.oliymahadquroncourse.service.core.ResourcePage;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static uz.oliymahad.oliymahadquroncourse.service.CourseService.COURSE;

@Service
@RequiredArgsConstructor
public class GroupService implements CRUDService<
        Long,
        APIResponse,
        GroupCreationRequest,
        GroupUpdateRequest >, PageProvider<APIResponse> {

    public static final String GROUP = "Group";

    private static final Logger log = LoggerFactory.getLogger(GroupService.class);

    private final GroupRepository groupRepository;

    private final CourseRepository courseRepository;

    private final QueueRepository queueRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Override
    public APIResponse create(GroupCreationRequest groupCreationRequest) {
        final Course course = courseRepository.findById(groupCreationRequest.getCourseId()).orElseThrow(() -> DataNotFoundException.of(COURSE, groupCreationRequest.getCourseId()));

        final Group group = modelMapper.map(groupCreationRequest, Group.class);
        group.setCourse(course);
        try {
            groupRepository.save(group);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new PersistenceException(exception);
        }

        return APIResponse.success(modelMapper.map(group, GroupResponse.class));
    }

    @Override
    public APIResponse get(Long id) {
        final Group group = groupRepository.findById(id).orElseThrow(() -> DataNotFoundException.of(GROUP, id));
        return APIResponse.success(modelMapper.map(group, GroupResponse.class));
    }

    @Override
    public APIResponse modify(Long id, GroupUpdateRequest groupUpdateRequest) {
        final Group group = groupRepository.findById(id).orElseThrow(() -> DataNotFoundException.of(GROUP, id));
        modelMapper.map(group, groupUpdateRequest);
        groupRepository.save(group);
        return APIResponse.success(modelMapper.map(group, GroupResponse.class));
    }

    @Override
    public APIResponse delete(Long id) {
        final Group group = groupRepository.findById(id).orElseThrow(() -> DataNotFoundException.of(GROUP, id));
        groupRepository.delete(group);
        return APIResponse.success(HttpStatus.OK.name());
    }

    public APIResponse addMembers(Long groupId, List<Long> userIds) {
        final Group group = groupRepository.findById(groupId).orElseThrow(() -> DataNotFoundException.of(GROUP, groupId));
        final List<User> users = userRepository.findAllById(userIds);
        if (group.getMembersCount() < users.size()) {
            throw new IllegalStateException("The number of members more than limit of " + group.getMembersCount());
        }
        List<Queue> queues = queueRepository.findAllByUserId(userIds);
        queues.forEach(queue -> queue.setQueueStatus(QueueStatus.ACCEPTED));
        try {
            queueRepository.saveAll(queues);
            group.setUsers(users);
            groupRepository.save(group);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new PersistenceException(exception);
        }
        return APIResponse.success(modelMapper.map(group, GroupResponse.class));
    }

    public APIResponse createGroupFromQueue(GroupCreationRequest request) {
        Group group = modelMapper.map(request, Group.class);
        final Page<Queue> page = queueRepository.findAll(PageRequest.of(0, request.getMembersCount(), Sort.by("appliedDate").ascending()));
        final List<Queue> queues = page.getContent();
        queues.forEach(queue -> queue.setQueueStatus(QueueStatus.ACCEPTED));
        group.setUsers(
                queues.stream().map(Queue::getUser).collect(Collectors.toList())
        );
        try{
            queueRepository.saveAll(queues);
            group = groupRepository.save(group);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new PersistenceException(exception);
        }
        return APIResponse.success(modelMapper.map(group, GroupResponse.class));
    }

    public APIResponse getGroupUsersData(Long groupId){
        final Group group = groupRepository.findById(groupId).orElseThrow(() -> DataNotFoundException.of(GROUP, groupId));
        return APIResponse.success(modelMapper.map(group.getUsers(), UserDataResponse[].class));
    }

    public APIResponse closeGroupOnSuccess(Long groupId){

        final Group group = groupRepository.findById(groupId).orElseThrow(() -> DataNotFoundException.of(GROUP, groupId));
        group.setGroupStatus(GroupStatus.COMPLETED);
        try{
            groupRepository.save(group);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new PersistenceException();
        }
        return APIResponse.success(modelMapper.map(group, GroupResponse.class));
    }

    public APIResponse delayGroupStartDate(Long groupId, Date delayDate){
        final Group group = groupRepository.findById(groupId).orElseThrow(() -> DataNotFoundException.of(GROUP, groupId));
        if(delayDate.before(group.getStartDate())){
            throw new IllegalStateException("The delay date must be after group-start date");
        }
        group.setStartDate(delayDate);
        try {
            groupRepository.save(group);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new PersistenceException();
        }
        return APIResponse.success(modelMapper.map(group, GroupResponse.class));
    }

    @Override
    public APIResponse pageOf(Pageable pageable) {
        final Page<Group> page = groupRepository.findAll(pageable);
        ResourcePage<GroupResponse> response = new ResourcePage<>();
        modelMapper.map(page,response);
        response.setContent(List.of(modelMapper.map(page.getContent(), GroupResponse[].class)));
        return APIResponse.success(response);
    }
}
