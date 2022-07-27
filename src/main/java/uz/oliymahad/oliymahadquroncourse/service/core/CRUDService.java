
package uz.oliymahad.oliymahadquroncourse.service.core;


import org.springframework.data.domain.Pageable;
import uz.oliymahad.oliymahadquroncourse.payload.Response;
import uz.oliymahad.oliymahadquroncourse.service.marker.Creationable;
import uz.oliymahad.oliymahadquroncourse.service.marker.Modifiable;

import javax.transaction.Transactional;

/**
 * @I - id of entities
 * @R - outcome response type which accessible data for usage
 * @C - incoming object that is request for creation
 * @U - incoming object that is request for update
 * */
public interface CRUDService<
        I,
        R extends Response,
        C extends Creationable,
        U extends Modifiable
        > extends Service{

    R create(final C c);

    R get(I id);

    R modify(I id, final U u);

    @Transactional
    R delete(I id);
}