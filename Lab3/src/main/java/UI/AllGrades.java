package UI;

import java.io.IOException;

public class AllGrades implements CommandInterface {

    private Menulist theCommand;

    public AllGrades(Menulist newCommand){
        this.theCommand = newCommand;
    }

    @Override
    public void execute() throws Exception {
        this.theCommand.allGradesUI();
    }
}
