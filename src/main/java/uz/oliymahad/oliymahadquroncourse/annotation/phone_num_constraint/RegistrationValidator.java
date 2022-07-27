package uz.oliymahad.oliymahadquroncourse.annotation.phone_num_constraint;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Service
public class RegistrationValidator implements ConstraintValidator<PhoneNumberOrEmail, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return isPhoneNumber(s) || isEmail(s);
    }

    public boolean isPhoneNumber(String s){
        Assert.notNull(s,"input must not be null");
        return s.length() == 13 && StringUtils.isNumeric(s.substring(1)) && s.indexOf("+998") == 0;
    }

    public boolean isEmail(String s){
        Assert.notNull(s,"input must not be null");
        return s.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
