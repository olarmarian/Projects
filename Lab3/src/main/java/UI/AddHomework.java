package UI;

public class AddHomework implements CommandInterface {

    private Menulist theCommand;

    public AddHomework(Menulist newCommand){
        this.theCommand = newCommand;
    }

    @Override
    public void execute() throws Exception {
        theCommand.addHomeworkUI();
    }
}
