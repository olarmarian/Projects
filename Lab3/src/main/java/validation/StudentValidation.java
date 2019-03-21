package validation;

import domain.Student;

public class StudentValidation implements IValidation<Student> {
    private String errors;
    @Override
    public void validate(Student elem) throws ValidationException {
        this.errors = "";
        if(elem.getId() == null){
            errors += "Student id is null\n";
        }
        if(elem.getId().equals("")){
            errors += "Student id is missing\n";
        }
        if(elem.getGroup() == null){
            errors += "Student group is null\n";
        }
        if(
                       !((elem.getGroup()>=211 && elem.getGroup()<=217) ||
                        (elem.getGroup()>=221 && elem.getGroup()<=227) ||
                        (elem.getGroup()>=231 && elem.getGroup()<=237) ||
                        (elem.getGroup()>=311 && elem.getGroup()<=317) ||
                        (elem.getGroup()>=321 && elem.getGroup()<=327) ||
                        (elem.getGroup()>=331 && elem.getGroup()<=337)||
                        (elem.getGroup()>=911 && elem.getGroup()<=917) ||
                        (elem.getGroup()>=921 && elem.getGroup()<=927) ||
                        (elem.getGroup()>=931 && elem.getGroup()<=937))
        ){
            errors += "Student group is invalid\n It has to be 211 < x < 217, 221 < x < 227, 231 < x < 237, 911 < x < 917, 921 < x < 927 or 931 < x < 937";
        }
        if(elem.getName() == null){
            errors += "Student name is null\n";
        }
        if(elem.getName().equals("") && elem.getName()!= null){
            errors += "Student name is missing\n";
        }
        if(elem.getEmail() == null){
            errors += "Student email is null\n";
        }
        if(elem.getTeacher() == null){
            errors += "Student teacher is null\n";
        }
        if(elem.getTeacher().equals("")){
            errors += "Student teacher is missing\n";

        }
        if(!(elem.getEmail().contains("@yahoo.com") || elem.getEmail().contains("@gmail.com") || elem.getEmail().contains("@yahoo.ro") || elem.getEmail().contains("@gmail.ro"))){
            errors += "Student email is invalid\n";
        }
        if(errors.length()!=0){
            throw new ValidationException(errors);
        }

    }
}
