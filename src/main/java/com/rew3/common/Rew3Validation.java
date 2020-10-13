package com.rew3.common;

import com.rew3.purchase.vendor.model.Vendor;
import com.rew3.sale.invoice.model.EditAction;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

public class Rew3Validation<T> {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public Rew3Validation() {
    }

    public boolean validateForAdd(T t) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, Default.class);
        constraintViolations.forEach(x -> System.out.println(x.getMessage()));
        if (constraintViolations.size() == 0) {
            return true;
        } else
            return false;

    }

    public boolean validateForUpdate(T t) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, EditAction.class);
        constraintViolations.forEach(x -> System.out.println(x.getMessage()));
        if (constraintViolations.size() == 0) {
            return true;
        } else return false;

    }
}
