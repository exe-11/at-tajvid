package uz.oliymahad.oliymahadquroncourse.entity.enums;

import lombok.Getter;

public enum QueueStatus {
    ON_WAITING(0),

    ACCEPTED(1),

    REJECTED(2),

    CANCELED(3),

    COMPLETED(4);

    public final int value;

    private static final String ERROR = "No such status value";

    QueueStatus(int value) {
        this.value = value;
    }

    public static QueueStatus getQueueStatus(int value){
        for (QueueStatus queueStatus : QueueStatus.values()) {
            if(queueStatus.value == value){
                return queueStatus;
            }
        }
        throw new IllegalArgumentException(ERROR);
    }

}
