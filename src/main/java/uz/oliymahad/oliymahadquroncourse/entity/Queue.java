package uz.oliymahad.oliymahadquroncourse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.entity.enums.QueueStatus;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false)
    private Date appliedDate = new Date();

    @Enumerated(EnumType.ORDINAL)
    private QueueStatus queueStatus = QueueStatus.ON_WAITING;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    public void setStatus(Integer queueStatus) {
        this.queueStatus = QueueStatus.getQueueStatus(queueStatus);
    }

    public void setStatus(QueueStatus queueStatus) {
        this.queueStatus = Queue.this.queueStatus;
    }

    public Queue(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    public Queue(User user, QueueStatus queueStatus, Course course) {
        this.user = user;
        this.queueStatus = queueStatus;
        this.course = course;
    }
}
