package UI;

import domain.Student;
import javafx.scene.control.Button;
import repository.AbstractCrudRepository;
import repository.CrudRepository;
import repository.StudentRepository;
import service.Service;
import validation.IValidation;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Predicate;

public class Application {

    public static class MenuLoader{
        Menulist menu;
        private TreeMap<String,Runnable> menuMap;
        private Service s;
        public MenuLoader(Service s){
            this.s=s;
            menu = Menu.getMenu(s);
            menuMap=new TreeMap<>();
            menu();
        }

        public void menu(){
            menuMap.put("1.Add a new student", ()->{
                AddStudent addS = new AddStudent(menu);
                MenuInvoker invoker = new MenuInvoker(addS);
                try {
                    invoker.executeCommand();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Add student -> failed");
                }
            });
            menuMap.put("2.Add a new homework",()->{
                AddHomework addH = new AddHomework(menu);
                MenuInvoker invoker = new MenuInvoker(addH);
                try {
                    invoker.executeCommand();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Add homework -> failed");
                }
            });
            menuMap.put("3.Print students",()->{
                AllStudents all = new AllStudents(menu);
                MenuInvoker invoker = new MenuInvoker(all);
                try {
                    invoker.executeCommand();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Print students -> failed");
                }
            });
            menuMap.put("4.Print homeworks",()->{
                AllHomeworks all = new AllHomeworks(menu);
                MenuInvoker invoker = new MenuInvoker(all);
                try {
                    invoker.executeCommand();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Print homeworks -> failed");
                }
            });
            menuMap.put("5.Update deadline",()->{
                UpdateDeadline upd = new UpdateDeadline(menu);
                MenuInvoker invoker = new MenuInvoker(upd);
                try {
                    invoker.executeCommand();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Update deadline -> failed");
                }
            });

            menuMap.put("8.Add grade",()->{
                AddGrade add = new AddGrade(menu);
                MenuInvoker invoker = new MenuInvoker(add);
                try {
                    invoker.executeCommand();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Add grade -> failed");
                }
            });

            menuMap.put("9.All grades",()->{
                AllGrades all = new AllGrades(menu);
                MenuInvoker invoker = new MenuInvoker(all);
                try {
                    invoker.executeCommand();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("All grades -> failed");
                }
            });

            menuMap.put("10.All students",()->{
                AllStudents all = new AllStudents(menu);
                MenuInvoker invoker = new MenuInvoker(all);
                try {
                    invoker.executeCommand();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("All students -> failed");
                }
            });

            menuMap.put("11.All homeworks",()->{
                AllHomeworks all = new AllHomeworks(menu);
                MenuInvoker invoker = new MenuInvoker(all);
                try {
                    invoker.executeCommand();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("All homeworks -> failed");
                }
            });

            menuMap.put("0.Menu",()->{
                for (String opt: menuMap.keySet()) {
                    System.out.println(opt);
                }
            });
        }

        public TreeMap<String,Runnable> getMenu(){
            return menuMap;
        }
    }


    public static void main(String[] args) throws Exception {
//
//        IValidation
//        CrudRepository<String, Student> sRepo = new StudentRepository() {
//            @Override
//            public List<Student> getPieceOfData(Integer start_idx, Integer final_idx) {
//                return null;
//            }
//        }
//        Service s= null;
//        try {
//            s = new Service("Student.txt","Teme.txt","NoteStudenti.txt");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        MenuLoader menu = new MenuLoader(s);
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        boolean condition = true;
//
//        menu.getMenu().get("0.Menu").run();
//        while (condition) {
//            System.out.print("Enter an option from menu:");
//            String command = br.readLine();
//            Predicate<String> keys=key->key.contains(command);
//            for (String key : menu.getMenu().keySet()) {
//                if (keys.test(key)) {
//                    menu.getMenu().get(key).run();
//                    break;
//                }
//            }
//        }
    }
}