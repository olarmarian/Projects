package UI;


import service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console implements Menulist {

    Service s;

    public Console(Service s){
        this.s=s;
    }

    @Override
    public void addStudentUI() throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Student's id :");
        String id = br.readLine();

        System.out.print("Student's name :");
        String name = br.readLine();

        System.out.print("Student' email :");
        String email = br.readLine();

        System.out.print("Student's group :");
        int gr = Integer.parseInt(br.readLine());

        System.out.print("Student's teacher :");
        String teacher = br.readLine();

        s.addStudent(id,name,gr,email,teacher);

    }

    @Override
    public void addHomeworkUI() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Homework's id :");
        int id = Integer.parseInt(br.readLine());

        System.out.print("Homeworks's requirement :");
        String req = br.readLine();

        System.out.print("Homework's deadline :");
        int dl = Integer.parseInt(br.readLine());

        System.out.print("Homework's course week :");
        int cw = Integer.parseInt(br.readLine());

        System.out.print("Homework's homework week :");
        int hw = Integer.parseInt(br.readLine());

        s.addHomework(id,req,dl,cw,hw);

    }

    @Override
    public void addGradeUI() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Grade's id student :");
        String idS = br.readLine();

        System.out.print("Grade's id homework :");
        int idH = Integer.parseInt(br.readLine());

        System.out.print("Grade's week homework :");
        String week = br.readLine();

        System.out.print("Grade's value :");
        Float grade = Float.parseFloat(br.readLine());

        System.out.print("Grade's feedback :");
        String feedback = br.readLine();

        System.out.print("Motivated weeks homework :");
        int mWeeks = Integer.parseInt(br.readLine());


        s.addGrade(idS,idH,grade,week,feedback,mWeeks);

    }



    @Override
    public void updateDeadlineUI() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Homework's id :");
        int idH = Integer.parseInt(br.readLine());

        s.updateDeadline(idH);
    }

    @Override
    public void allStudentsUI() throws Exception {
        s.allStudents().forEach(s -> System.out.println(s.toString()));
    }

    @Override
    public void allHomeworksUI() throws Exception {
        s.allHomeworks().forEach(h-> System.out.println(h.toString()));
    }

    @Override
    public void allGradesUI() throws Exception {
        s.allGrades().forEach(g-> System.out.println(g.toString()));
    }
}

