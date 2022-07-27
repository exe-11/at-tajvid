package uz.oliymahad.oliymahadquroncourse.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.Section;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Role;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;
import uz.oliymahad.oliymahadquroncourse.payload.request.section.RolePermission;
import uz.oliymahad.oliymahadquroncourse.payload.request.section.SectionRequest;
import uz.oliymahad.oliymahadquroncourse.payload.response.section.Content;
import uz.oliymahad.oliymahadquroncourse.payload.response.section.SectionAccessResponse;
import uz.oliymahad.oliymahadquroncourse.payload.response.section.SectionPermission;
import uz.oliymahad.oliymahadquroncourse.payload.response.section.SectionResponse;
import uz.oliymahad.oliymahadquroncourse.repository.SectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.oliymahad.oliymahadquroncourse.entity.enums.Role.*;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    private Section editSection(SectionRequest sectionRequest, Section section) {
        for (RolePermission rolePermission : sectionRequest.getRolePermissionList()) {
            int flag = getRole(rolePermission.getRoleName()).flag;

            if (rolePermission.getPermissionRequest().isVisibility()){
                section.setVisibility(section.getVisibility() | flag);
            }
            if (rolePermission.getPermissionRequest().isUpdate()){
                section.setUpdate(section.getUpdate() | flag);
            }
            if (rolePermission.getPermissionRequest().isDelete()){
                section.setDelete(section.getDelete() | flag);
            }
            if (rolePermission.getPermissionRequest().isInfo()){
                section.setInfo(section.getInfo() | flag);
            }
        }
        return section;
    }


    public void addSection(SectionRequest sectionRequest) {

        Optional<Section> sections = sectionRepository.findByName(sectionRequest.getSectionName());
        if (sections.isPresent()) {
            sectionRepository.save(editSection(sectionRequest, sections.get()));
        } else {
            Section sections1 = new Section();
            sections1.setName(sectionRequest.getSectionName());
            sectionRepository.save(editSection(sectionRequest, sections1));
        }
    }

    public APIResponse getList() {
        List<Section> sectionList = sectionRepository.findAll();
        List<SectionResponse> sectionResponseList = new ArrayList<>();
        for (Section sections : sectionList) {
            sectionResponseList.add((SectionResponse) getSection(sections.getId()).getData());
        }
        return APIResponse.success(sectionResponseList);
    }

    public APIResponse getSection(Long id) {
        final Section section = sectionRepository.findById(id).orElseThrow(() -> DataNotFoundException.of("Section", id));

        final List<Content> contentList = new ArrayList<>();

        for (Role role : values()) {
            final SectionPermission permissionToSection = getPermissionToSection(role, section);
            contentList.add(new Content(role.flag, role.name(), permissionToSection));
        }
        return APIResponse.success(new SectionResponse(section.getId(), section.getName(), contentList));
    }

    public SectionPermission getPermissionToSection(Role role, Section sections) {
        int flag = role.flag;
        return SectionPermission.builder()
                .visibility((flag & sections.getVisibility()) > 0)
                .update((flag & sections.getUpdate()) > 0)
                .delete((flag & sections.getDelete()) > 0)
                .info((flag & sections.getInfo()) > 0)
                .build();
    }

    public List<SectionAccessResponse> getAccessForSections() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int number = principal.getRoles();
        List<SectionAccessResponse> responseList = new ArrayList<>();
        for (Section section : sectionRepository.findAll()) {
            if ((section.getVisibility() & number) > 0) {
                responseList.add(new SectionAccessResponse(section.getId(), section.getName()));
            }
        }
        return responseList;
    }
}
