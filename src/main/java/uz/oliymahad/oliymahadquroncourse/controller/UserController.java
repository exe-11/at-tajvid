package uz.oliymahad.oliymahadquroncourse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oliymahad.oliymahadquroncourse.controller.core.AbstractCRUDController;
import uz.oliymahad.oliymahadquroncourse.controller.core.PageController;
import uz.oliymahad.oliymahadquroncourse.payload.request.user.UserSigningRequest;
import uz.oliymahad.oliymahadquroncourse.payload.request.user.UserUpdateRequest;
import uz.oliymahad.oliymahadquroncourse.service.UserService;

import static uz.oliymahad.oliymahadquroncourse.controller.core.ControllerUtils.USERS_URI;

@RestController
@RequestMapping(USERS_URI)
public class UserController extends AbstractCRUDController<
        UserService,
        Long,
        UserSigningRequest,
        UserUpdateRequest > implements PageController<UserService> {

    protected UserController(UserService service) {
        super(service);
    }

}
