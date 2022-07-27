package uz.oliymahad.oliymahadquroncourse.payload.request.queue;

import lombok.Getter;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.service.marker.Creationable;


@Getter
@Setter
public class QueueCreationRequest implements Creationable{
    private Long userId;

    private Long courseId;
}
