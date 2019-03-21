package validation;

import domain.Grade;

import java.util.ArrayList;

public class GradeValidation implements  IValidation<Grade>{
    private String errors;
    @Override
    public void validate(Grade elem) throws ValidationException {
        this.errors = "";
        if(elem.getId().getValue().getId() == null){
            errors += "Grade id homework is null\n";
        }

        if(elem.getId().getValue().getId()<0){
            errors += "Grade id homework is invalid\n";
        }

        if(elem.getGrade() == null){
            errors += "Grade value is null\n";
        }

        if( (elem.getGrade() > elem.getMaxGrade() ) || elem.getGrade()<0 || elem.getGrade() < 5){
            errors += "Grade value is invalid\n";
        }

        if(elem.getId().getKey().getId() == null){
            errors += "Grade id student is null\n";
        }
        if(elem.getId().getKey().getId().equals("")){
            errors += "Grade id student is missing\n";
        }

        if(elem.getFeedback().equals("")){
            errors += "Grade feedback is missing\n";
        }

        if(elem.getFeedback() == null){
            errors += "Grade feedback is null\n";
        }
        if(errors.length()!=0){
            throw new ValidationException(errors);
        }

    }
}
