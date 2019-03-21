package domainTest;

import domain.Grade;
import domain.Homework;
import domain.Student;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import static org.junit.Assert.*;

public class GradeTest {
    Grade g1,g2;
    Student s1,s2;
    Homework h1, h2;
    @Before
    public void setUp() throws Exception {
        s1 = new Student("1","a1",222,"as1@yahoo.com","b1");
        s2 = new Student("2","a2",222,"as2@yahoo.com","b2");
        h1 = new Homework(1,"req1",1,1,1);
        h2 = new Homework(2,"req1",1,1,1);
        g1 = new Grade(s1,h1,8f,"2014-12-22","asd");
        g2 = new Grade(s2,h2,7f,"2014-12-22","asd");
    }

    @After
    public void tearDown() throws Exception {
        g1 = null;
        g2 = null;
    }

    @Test
    public void getId() {
        Pair<Student, Homework> p1 = new Pair<>(s1,h1);
        assertTrue(g1.getId().getKey()==p1.getKey());
        assertTrue(g1.getId().getValue()==p1.getValue());
    }

    @Test
    public void setId() {
        Pair<Student, Homework> p1 = new Pair<>(s2,h2);
        g1.setId(p1);
        assertTrue(g1.getId().getKey()==s2);
    }

    @Test
    public void getGrade() {
        assertTrue(g1.getGrade()==8f);
    }

    @Test
    public void setGrade() {
        g1.setGrade(9f);
        assertTrue(g1.getGrade()==9f);
    }

    @Test
    public void getFeedback() {
        assertTrue(g1.getFeedback().equals("asd"));
    }

    @Test
    public void setFeedback() {
        g1.setFeedback("bbb");
        assertTrue(g1.getFeedback().equals("bbb"));
    }

    @Test
    public void getMaxGrade() {
        assertTrue(g1.getMaxGrade()==10f);
    }

    @Test
    public void getWeek() {
        assertTrue(g1.getDate().toString().equals("2014-12-22"));
    }

    @Test
    public void setWeek() {
        g1.setDate("2014-12-22");
        assertTrue(g1.getDate().toString().equals("2014-12-22"));
    }

    @Test
    public void decMaxGrade() {
        LocalDate start = LocalDate.of(2018, 10, 1).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        long w = ChronoUnit.WEEKS.between(start,g1.getDate());
        g1.decMaxGrade(4,(int)w);
        assertTrue(g1.getMaxGrade()!=10f-2.5f);
    }

    @Test
    public void equals() {
        assertTrue(!g1.equals(g2));
        assertTrue(g1.hashCode() != g2.hashCode());
    }

}