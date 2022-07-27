package uz.oliymahad.oliymahadquroncourse.controller.core;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.oliymahad.oliymahadquroncourse.service.core.PageProvider;

public interface PageController<S extends PageProvider> {

    @GetMapping("/pages")
    default ResponseEntity<?> pageOf(
            S service,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "DESC") String order,
            @RequestParam(required = false) String[] properties
    ) {
        return ResponseEntity.ok(
                (properties == null || properties.length == 0) ?
                        service.pageOf(PageRequest.of(page, size)) :
                        service.pageOf(PageRequest.of(page, size, Sort.Direction.valueOf(order), properties))
        );
    }

}
