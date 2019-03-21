package UI;

public class AddGrade implements CommandInterface {

    private Menulist theCommand;

    public AddGrade(Menulist newCommand){
        this.theCommand = newCommand;
    }

    @Override
    public void execute() throws Exception {
        theCommand.addGradeUI();
    }
}
