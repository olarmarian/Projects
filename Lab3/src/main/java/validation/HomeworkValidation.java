package validation;

import domain.Homework;


public class HomeworkValidation implements IValidation<Homework> {
    private String errors;
    @Override
    public void validate(Homework elem) throws ValidationException {
        this.errors = "";
        if(elem.getId() == null){
            errors += "Homework id is null\n";
        }

        if(elem.getId().equals("")){
            errors += "Homework id is missing\n";
        }
        if(elem.getRequirement() == null){
            errors += "Homework requirement is null\n";
        }

        if(elem.getRequirement().equals("")){
            errors += "Homework requirement is missing\n";
        }

        if(elem.getCourseWeek() == null){
            errors += "Homework course week is null\n";
        }

        if(elem.getCourseWeek()<1 || elem.getCourseWeek()>14){
            errors += "Homework course week is invalid\n";
        }

        if(elem.getHomeworkWeek() == null){
            errors += "Homework week is null\n";
        }

        if(elem.getHomeworkWeek()<1 || elem.getHomeworkWeek()>14){
            errors += "Homework week is invalid\n";
        }

        if(elem.getDeadline() == null){
            errors += "Homework deadline is null\n";
        }
        if(elem.getDeadline()<1 || elem.getDeadline()>14 || elem.getDeadline()<elem.getCourseWeek()){
            errors += "Homework deadline is invalid\n";
        }

        if (errors.length() !=0){
            throw new ValidationException(errors);
        }
    }
}
