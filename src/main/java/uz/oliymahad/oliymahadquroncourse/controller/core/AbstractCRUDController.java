package uz.oliymahad.oliymahadquroncourse.controller.core;

import org.springframework.http.ResponseEntity;
import uz.oliymahad.oliymahadquroncourse.service.core.CRUDService;
import uz.oliymahad.oliymahadquroncourse.service.marker.Creationable;
import uz.oliymahad.oliymahadquroncourse.service.marker.Modifiable;

public abstract class AbstractCRUDController<
        S extends CRUDService,
        I,
        C extends Creationable,
        U extends Modifiable> implements CRUDController<I, C, U> {

    protected final S service;

    protected AbstractCRUDController(S service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<?> create(C c) {
        return ResponseEntity.ok(service.create(c));
    }

    @Override
    public ResponseEntity<?> get(I id) {
        return ResponseEntity.ok(service.get(id));
    }

    @Override
    public ResponseEntity<?> modify(I id, U u) {
        return ResponseEntity.ok(service.modify(id, u));
    }

    @Override
    public ResponseEntity<?> delete(I id) {
        return ResponseEntity.ok(service.delete(id));
    }

}
