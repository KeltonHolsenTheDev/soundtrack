package soundtrack.validation;

import soundtrack.models.Event;
import soundtrack.models.UserRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class NullItemsValidator implements ConstraintValidator<NoNullItemsInList, List<UserRole>> {


    @Override
    public void initialize(NoNullItemsInList constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<UserRole> staffAndRoles, ConstraintValidatorContext constraintValidatorContext) {
        if(staffAndRoles == null) {
            return true;
        }

        for(UserRole userRole: staffAndRoles) {
            if (userRole == null) {
                return false;
            }
        }

        return true;
    }
}
