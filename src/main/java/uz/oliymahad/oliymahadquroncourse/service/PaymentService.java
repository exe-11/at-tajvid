package uz.oliymahad.oliymahadquroncourse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.Queue;
import uz.oliymahad.oliymahadquroncourse.entity.enums.QueueStatus;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.repository.QueueRepository;

import javax.persistence.PersistenceException;
import static uz.oliymahad.oliymahadquroncourse.service.QueueService.QUEUE;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final QueueRepository queueRepository;


    public boolean isPaymentCompleted(Long userId, Long courseId) {
//  TODO - add payment system to confirm payment;
        return true;
    }

    @Async
    public void payForCourse(Long userId, Long courseId) {
//  sending request for payment and get result if result is Ok
        if (true) {
            final Queue queue = queueRepository.findByUserId(userId).orElseThrow(() -> DataNotFoundException.of(QUEUE, "user id " + userId));
            queue.setQueueStatus(QueueStatus.COMPLETED);
            try {
                queueRepository.save(queue);
            }catch (Exception exception){
                throw new PersistenceException(exception);
            }
        }
    }

}
