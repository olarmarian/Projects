import UI.Gui.FXMLController;
import domain.Grade;
import domain.Homework;
import domain.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import repository.CrudRepository;
import repository.GradeDBRepository;
import repository.HomeworkDBRepository;
import repository.StudentDBRepository;
import service.Service;
import validation.GradeValidation;
import validation.HomeworkValidation;
import validation.IValidation;
import validation.StudentValidation;


public class FXMLMain extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        IValidation<Student> studentValidator = new StudentValidation();
        IValidation<Homework> homeworkIValidator = new HomeworkValidation();
        IValidation<Grade> gradeIValidator = new GradeValidation();

        CrudRepository<String, Student> studentRepo =  new StudentDBRepository(studentValidator);;
        CrudRepository<Integer, Homework> homeworkRepo = new HomeworkDBRepository(homeworkIValidator);
        CrudRepository<Pair<Student,Homework>, Grade> gradeRepo = new GradeDBRepository(gradeIValidator,studentRepo,homeworkRepo);

        Service service = new Service(studentRepo,homeworkRepo,gradeRepo);

        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main.fxml"));
        root = loader.load();
        FXMLController ctrl = loader.getController();
        ctrl.setFXMLController(service);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Lab Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
