package uz.oliymahad.oliymahadquroncourse.annotation.phone_num_constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RegistrationValidator.class})
public @interface PhoneNumberOrEmail {
    public String message() default "Invalid phone number format";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
