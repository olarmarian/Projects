package UI;

import java.io.IOException;

public class AllStudents implements CommandInterface {

    private Menulist theCommand;

    public AllStudents(Menulist newCommand){
        this.theCommand = newCommand;
    }

    @Override
    public void execute() throws Exception {
        this.theCommand.allStudentsUI();
    }
}
