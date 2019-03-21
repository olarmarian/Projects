package domainTest;

import domain.Homework;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HomeworkTest {

    Homework h1;
    Homework h2;
    @Before
    public void setUp() throws Exception {
        h1 = new Homework(1,"cerinta1",3,3,3);
        h2 = new Homework(1,"cerinta2",5,5,5);
    }


    @After
    public void tearDown() throws Exception {
        h1 = null;
        h2 = null;
    }

    @Test
    public void testHomework(){
        assertTrue(h1.getId()==1);
        assertTrue(h1.getRequirement().equals("cerinta1"));
        assertTrue(h1.getDeadline()==3);
        assertTrue(h1.getHomeworkWeek()==3);
        assertTrue(h1.getCourseWeek()==3);


        assertTrue(h1==h1);
        h1.setDeadline(h2.getDeadline());
        h1.setCourseWeek(h2.getCourseWeek());
        h1.setRequirement(h2.getRequirement());
        h1.setHomeworkWeek(h2.getHomeworkWeek());

        assertTrue(h2.hashCode()==h1.hashCode());

    }
}