package repository;

import domain.Homework;
import validation.IValidation;

import java.io.IOException;
import java.util.List;

public abstract class HomeworkRepository extends AbstractCrudRepository<Integer, Homework> {
    public HomeworkRepository(IValidation<Homework> validator, String fileName,String obj) throws Exception {
        super(validator,fileName,obj);
    }

    public abstract List<Homework> getPieceOfData(Integer start_idx,Integer final_idx);
}