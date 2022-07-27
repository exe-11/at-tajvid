package uz.oliymahad.oliymahadquroncourse.payload.request.queue;


import lombok.Getter;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.service.marker.Modifiable;

@Getter
@Setter
public class QueueUpdateRequest implements Modifiable {

    private Long userId;

    private Integer queueStatus;

    private Long courseId;

}
