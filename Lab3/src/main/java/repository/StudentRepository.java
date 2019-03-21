package repository;

import domain.Student;
import validation.IValidation;

import java.io.IOException;
import java.util.List;

public abstract class StudentRepository extends AbstractCrudRepository<String, Student> {
        public StudentRepository(IValidation<Student> validator, String fileName,String ob) throws Exception {
            super(validator,fileName,ob);
        }
}