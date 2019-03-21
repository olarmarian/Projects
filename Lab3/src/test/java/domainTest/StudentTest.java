package domainTest;

import domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {

    Student s1;
    Student s2;
    @Before
    public void setUp() throws Exception {
        s1 = new Student("01","nume1",221,"asd@yahoo.com","prof1");
        s2 = new Student("01","nume2",223,"asd@gmail.com","prof2");
    }

    @After
    public void tearDown() throws Exception {
        s1 = null;
        s2 = null;
    }

    @Test
    public void testStudent(){
        assertTrue(s1.getId().equals("01"));
        assertTrue(s1.getName().equals("nume1"));
        assertTrue(s1.getEmail().equals("asd@yahoo.com"));
        assertTrue(s1.getTeacher().equals("prof1"));
        assertTrue(s1.getGroup()==221);


        assertTrue(s1.equals(s2));
        s2.setGroup(s1.getGroup());
        s2.setName(s1.getName());
        s2.setEmail(s1.getEmail());
        s2.setTeacher(s1.getTeacher());

        assertTrue(s1.hashCode()==s2.hashCode());
    }
}