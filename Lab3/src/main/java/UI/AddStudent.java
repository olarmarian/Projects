package UI;

public class AddStudent implements CommandInterface {

    private Menulist theCommand;

    public AddStudent(Menulist newCommand){
        this.theCommand = newCommand;
    }

    @Override
    public void execute() throws Exception {
        theCommand.addStudentUI();
    }
}
