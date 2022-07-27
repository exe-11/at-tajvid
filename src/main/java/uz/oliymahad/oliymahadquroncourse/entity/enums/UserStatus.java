package uz.oliymahad.oliymahadquroncourse.entity.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE(1),
    BLOCKED(-1),
    DELETED(-2),
    PENDING(0);

    public final int value;
    UserStatus(int value) {
        this.value = value;
    }
}
