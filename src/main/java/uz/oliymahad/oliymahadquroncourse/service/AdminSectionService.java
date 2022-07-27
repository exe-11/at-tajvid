package uz.oliymahad.oliymahadquroncourse.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.Section;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;
import uz.oliymahad.oliymahadquroncourse.payload.response.section.AdminSection;
import uz.oliymahad.oliymahadquroncourse.payload.response.section.SectionPermission;
import uz.oliymahad.oliymahadquroncourse.repository.SectionRepository;

import java.util.List;

import static uz.oliymahad.oliymahadquroncourse.service.marker.PanelSectionConstants.*;


@Service
@RequiredArgsConstructor
public class AdminSectionService {

    private final SectionRepository sectionRepository;

    private final ModelMapper modelMapper;

    private final CourseService courseService;

    private final GroupService groupService;

    private final QueueService queueService;

    private final UserService userService;


    public APIResponse getSection(Long id, Pageable pageable) {
        final Section section = sectionRepository.findById(id).orElseThrow(() -> DataNotFoundException.of("Section", id));

        AdminSection adminSection = new AdminSection();
        switch (section.getName()) {
            case USER:
                adminSection = getUsers(pageable, section);
                break;
            case GROUP:
                adminSection = getGroup(pageable, section);
                break;
            case COURSE:
                adminSection = getCourse(pageable, section);
                break;
            case QUEUE:
                adminSection = getQueue(pageable, section);
                break;
        }
        return APIResponse.success(adminSection);
    }

    public AdminSection getUsers(Pageable pageable, Section section) {
        APIResponse apiResponse = userService.pageOf(pageable);
        AdminSection adminSection = new AdminSection();
        adminSection.setHeaders(List.of("id", "firstName", "lastName", "middleName", "phoneNumber"));
        adminSection.setBody(apiResponse.getData());
        modelMapper.map(getPermission(section), adminSection);
        return adminSection;
    }

    public AdminSection getCourse(Pageable pageable, Section section) {
        AdminSection adminSection = new AdminSection();
        adminSection.setHeaders(List.of("id", "name", "description", "price", "duration"));
        APIResponse apiResponse = courseService.pageOf(pageable);
        adminSection.setBody(apiResponse.getData());
        modelMapper.map(getPermission(section), adminSection);
        return adminSection;
    }

    public AdminSection getGroup(Pageable pageable, Section section) {
        AdminSection adminSection = modelMapper.map(getPermission(section), AdminSection.class);
        adminSection.setHeaders(List.of("id", "name", "memberCount", "startDate", "courseName", "courseId"));
        APIResponse apiResponse = groupService.pageOf(pageable);
        adminSection.setBody(apiResponse.getData());
        return adminSection;
    }

    public AdminSection getQueue(Pageable pageable, Section section) {
        AdminSection adminSection = new AdminSection();
        adminSection.setHeaders(List.of("id", "userId", "firstName", "lastName", "phoneNumber", "courseName", "appliedDate", "endDate"));
        adminSection.setBody(queueService.pageOf(pageable).getData());


        modelMapper.map(getPermission(section), adminSection);
        return adminSection;
    }

    public SectionPermission getPermission(Section section) {
        SecurityContext context = SecurityContextHolder.getContext();
        User principal = (User) context.getAuthentication().getPrincipal();
        int flag = principal.getRoles();
        return SectionPermission.builder()
                .visibility((flag & section.getVisibility()) > 0)
                .update((flag & section.getUpdate()) > 0)
                .delete((flag & section.getDelete()) > 0)
                .info((flag & section.getInfo()) > 0)
                .build();
    }
}
