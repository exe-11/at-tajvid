package uz.oliymahad.oliymahadquroncourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.oliymahadquroncourse.payload.request.section.SectionRequest;
import uz.oliymahad.oliymahadquroncourse.service.AdminSectionService;
import uz.oliymahad.oliymahadquroncourse.service.SectionService;

import javax.validation.Valid;

import static uz.oliymahad.oliymahadquroncourse.controller.core.ControllerUtils.SECTIONS_URI;

@RestController
@RequestMapping(SECTIONS_URI)
@RequiredArgsConstructor
public class SectionController{
    private final SectionService sectionService;
    private final AdminSectionService adminSectionService;

    @PostMapping("/edit")
    public Boolean addSection(@RequestBody  @Valid SectionRequest sectionRequest) {
        sectionService.addSection(sectionRequest);
        return true;
    }

    @GetMapping()
    public ResponseEntity<?> getAccessForSections() {
        return ResponseEntity.ok(sectionService.getAccessForSections());
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSections() {
        return  ResponseEntity.ok(sectionService.getList());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSectionById (
            @PathVariable Long id)
    {
        return ResponseEntity.ok(sectionService.getSection(id));
    }

    @GetMapping("/data")
    public ResponseEntity<?> getSections(
            @RequestParam Long id,
             Pageable pageable
    ){
        return ResponseEntity.ok().body(adminSectionService.getSection(id,pageable));
    }
}
