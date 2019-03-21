package repositoryTest;

import domain.Homework;
import domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.AbstractCrudRepository;
import repository.HomeworkRepository;
import repository.StudentRepository;
import validation.HomeworkValidation;
import validation.IValidation;
import validation.StudentValidation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AbstractCrudRepositoryTest {

    StudentRepository studRepo;
    HomeworkRepository homeRepo;
    IValidation<Student> val1;
    IValidation<Homework> val2;
    @Before
    public void setUp() throws Exception {
        val1 = new StudentValidation();
        val2 = new HomeworkValidation();
        studRepo = new StudentRepository(val1, "testRepoStudenti.txt", "domain.Student") {
            @Override
            public List<Student> getPieceOfData(Integer start_idx, Integer final_idx) {
                return null;
            }
        };
        homeRepo = new HomeworkRepository(val2, "testRepoHomework.txt", "domain.Homework") {
            @Override
            public List<Homework> getPieceOfData(Integer start_idx, Integer final_idx) {
                return null;
            }
        };
//
//        studRepo.save(new Student("01","nume1",221,"asd@yahoo.com","prof1"));
//        studRepo.save(new Student("02","nume2",221,"asd@yahoo.com","prof2"));

//        homeRepo.save(new Homework(1,"cerinta1",2,2,2));
//        homeRepo.save(new Homework(2,"cerinta2",2,2,2));
    }

    @After
    public void tearDown() throws Exception {
        val1 = null;
        val2 = null;
        studRepo = null;
        homeRepo = null;
    }

    @Test
    public void save() {
        try {
            studRepo.save(new Student("03","nume3",221,"asd@yahoo.com","prof3"));
            homeRepo.save(new Homework(3,"cerinta3",2,2,2));
        } catch (IOException e) { }
        catch (Exception e) { }
        try {
            assertTrue(studRepo.size()==2);
            assertTrue(homeRepo.size()==2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void findOne() throws Exception {
            assertTrue(studRepo.findOne("03").getName().equals("nume3"));
            assertTrue(homeRepo.findOne(2).getDeadline()==2);
    }

    @Test
    public void findAll() throws Exception {
        Iterable<Student> it1 = null;
        Iterable<Homework> it2 = null;
        it1 = studRepo.findAll();
        it2 = homeRepo.findAll();

        List<Student> l1 = new ArrayList<>();
        List<Homework> l2 = new ArrayList<>();

        for ( Student s : it1){
            l1.add(s);
        }
        for ( Homework h : it2){
            l2.add(h);
        }

        assertTrue(l1.size()==2);
        assertTrue(l2.size()==2);

    }

    @Test
    public void delete() throws Exception {
        try {
            homeRepo.delete(1);
            studRepo.delete("01");
        } catch (IOException e) { }
        Iterable<Student> it1 = null;
        Iterable<Homework> it2 = null;
        it1 = studRepo.findAll();
        it2 = homeRepo.findAll();

        List<Student> l1 = new ArrayList<>();
        List<Homework> l2 = new ArrayList<>();

        for ( Student s : it1){
            l1.add(s);
        }
        for ( Homework h : it2){
            l2.add(h);
        }

        assertTrue(l1.size()==2);
        assertTrue(l2.size()==2);

    }

    @Test
    public void update() throws Exception {
        Student s = studRepo.findOne("03");
        Homework h = homeRepo.findOne(2);

        s.setTeacher("Boi");
        studRepo.update(s);
        s = studRepo.findOne("03");
        assertTrue(s.getTeacher().equals("Boi"));

    }

    @Test
    public void size() throws Exception {
        Iterable<Student> it1 = null;
        it1 = studRepo.findAll();

        List<Student> l1 = new ArrayList<>();

        for ( Student s : it1){
            l1.add(s);
        }
        assertTrue(l1.size()==2);

    }
}