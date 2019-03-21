package UI;

import java.io.IOException;

public interface Menulist {

    public void addStudentUI() throws Exception;

    public void addHomeworkUI() throws IOException, Exception;

    public void addGradeUI() throws IOException, Exception;

    public void updateDeadlineUI() throws Exception;

    public void allStudentsUI() throws Exception;

    public void allHomeworksUI() throws Exception;

    public void allGradesUI() throws Exception;
}
