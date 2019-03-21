package repository;

import domain.Grade;
import domain.Homework;
import domain.Student;
import javafx.util.Pair;
import validation.IValidation;
import validation.StudentValidation;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
public abstract class GradesRepository extends AbstractCrudRepository<Pair<Student,Homework>, Grade> {
    protected StudentRepository sRepo;
    protected HomeworkRepository hRepo;
    public GradesRepository(IValidation<Grade> validator, String fileName, String ob,StudentRepository sr, HomeworkRepository hr) throws Exception {
        this.filename = fileName;
        this.objtype = ob;
        this.sRepo = sr;
        this.hRepo = hr;
        this.validator = validator;
        this.entities = new HashMap<>();
        readData();
    }
    public abstract List<Grade> getPieceOfData(Integer start_idx, Integer final_idx);

    @Override
    protected void readData(){
        entities.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null){
                Class<?> itemClass = Class.forName(this.objtype);
                Object item = itemClass.newInstance();

                Student s = sRepo.findOne(line.split("#")[0]);
                Homework h = hRepo.findOne(Integer.parseInt(line.split("#")[1]));
                Method method = itemClass.getDeclaredMethod("buildObject",Student.class,Homework.class, String.class);
                Grade obj = (Grade)method.invoke(item, s, h,line);
                entities.put(obj.getId(),obj);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error open file");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void save(Grade entity) throws Exception {
        this.validator.validate(entity);
        if (this.entities.putIfAbsent(entity.getId(),entity)!=null) {
            throw new Exception("Duplicate entity");
        }
        writeData();
        String name = entity.getId().getKey().getName() + ".txt";
        this.writeIn(entity, name);
    }


    public void writeIn(Grade g , String name) throws IOException {
        try {
            File fis = new File(name);
            if (fis.exists() == false) {
                PrintWriter pr = new PrintWriter(fis, "UTF-8");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fis, true));
            writer.write("Tema: " + g.getId().getValue());
            writer.newLine();
            writer.write("Nota: " + g.getGrade());
            writer.newLine();
            writer.write("Predata in data: " + g.getDate());
            writer.newLine();
            writer.write("Feedback: ");
            writer.write(g.getFeedback());
            writer.newLine();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
