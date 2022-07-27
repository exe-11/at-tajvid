package uz.oliymahad.oliymahadquroncourse.entity.enums;

public enum GroupStatus {
    COMPLETED(0),

    IN_PROGRESS(1),
    UPCOMING(2);

    public final int value;

    GroupStatus(int value) {
        this.value = value;
    }
}
