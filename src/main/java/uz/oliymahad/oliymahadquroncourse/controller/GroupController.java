package uz.oliymahad.oliymahadquroncourse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oliymahad.oliymahadquroncourse.controller.core.AbstractCRUDController;
import uz.oliymahad.oliymahadquroncourse.controller.core.PageController;
import uz.oliymahad.oliymahadquroncourse.payload.request.queue.QueueCreationRequest;
import uz.oliymahad.oliymahadquroncourse.payload.request.queue.QueueUpdateRequest;
import uz.oliymahad.oliymahadquroncourse.service.GroupService;

import static uz.oliymahad.oliymahadquroncourse.controller.core.ControllerUtils.GROUPS_URI;


@RestController
@RequestMapping(GROUPS_URI)
public class GroupController extends AbstractCRUDController <
        GroupService,
        Long,
        QueueCreationRequest,
        QueueUpdateRequest >  implements PageController<GroupService> {
    protected GroupController(GroupService service) {
        super(service);
    }


}
