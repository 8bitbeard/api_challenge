package br.com.itau.challenge.validators;

import br.com.itau.challenge.entities.PurchaseType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PurchaseTypeSubSetValidator implements ConstraintValidator<PurchaseTypeSubset, PurchaseType> {
    private PurchaseType[] subset;

    @Override
    public void initialize(PurchaseTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(PurchaseType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}