package uz.oliymahad.oliymahadquroncourse.controller.core;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.oliymahadquroncourse.service.marker.Creationable;
import uz.oliymahad.oliymahadquroncourse.service.marker.Modifiable;

import javax.validation.Valid;


public interface CRUDController<I, C extends Creationable, U extends Modifiable> {

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid C c);

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable I id);


    @PutMapping("/{id}")
    ResponseEntity<?> modify(@PathVariable I id, @RequestBody @Valid U u);


    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable I id);

}
