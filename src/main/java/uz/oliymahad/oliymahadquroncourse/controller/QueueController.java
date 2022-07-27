package uz.oliymahad.oliymahadquroncourse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.oliymahadquroncourse.controller.core.AbstractCRUDController;
import uz.oliymahad.oliymahadquroncourse.controller.core.PageController;
import uz.oliymahad.oliymahadquroncourse.payload.request.queue.QueueCreationRequest;
import uz.oliymahad.oliymahadquroncourse.payload.request.queue.QueueUpdateRequest;
import uz.oliymahad.oliymahadquroncourse.service.QueueService;

import static uz.oliymahad.oliymahadquroncourse.controller.core.ControllerUtils.QUEUES_URI;


@RestController
@RequestMapping(QUEUES_URI)
public class QueueController extends AbstractCRUDController<
        QueueService,
        Long,
        QueueCreationRequest,
        QueueUpdateRequest> implements PageController<QueueService>
{

    public QueueController(QueueService service) {
        super(service);
    }

    @GetMapping("/user_details/{queueId}")
    public ResponseEntity<?> getUserDetails(@PathVariable long queueId) {
        return ResponseEntity.ok(service.getUserDetailsFromQueue(queueId));
    }


    @PatchMapping("/status")
    public ResponseEntity<?> changeStatus(
            @RequestParam Long id,
            @RequestParam(name = "status_val") Integer statusValue
    ) {
        return ResponseEntity.ok(service.updateQueueState(id, statusValue));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<?> cancelQueue(
            @RequestParam Long id
    ){
        return ResponseEntity.ok(service.cancelQueue(id));
    }

    @PatchMapping("/reject")
    public ResponseEntity<?> rejectQueue(
            @RequestParam Long id
    ){
        return ResponseEntity.ok(service.rejectQueue(id));
    }

//    @GetMapping("/user/{userId}/course/{courseId}") //userning shu kursda neccinci orinda turganini korsatadi
//    public ResponseEntity<?> getUserCourseQueue(
//            @PathVariable Long userId,
//            @PathVariable Long courseId
//    ){
//        RestAPIResponse apiResponse = queueService.getUserCourseQueue(userId,courseId);
//        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
//    }
//
//    @GetMapping(GET_USERS_BY_FILTER)
//    public ResponseEntity<?> getUsersByFilter(@RequestBody FilterQueueForGroupsDTO filterQueueDTO){
//        ApiResponse<List<Long>> apiResponse = queueService.getUsersByFilter(filterQueueDTO);
//        return ResponseEntity.status(apiResponse.isStatus() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
//    }





}
