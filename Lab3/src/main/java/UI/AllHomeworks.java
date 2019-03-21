package UI;

import java.io.IOException;

public class AllHomeworks implements CommandInterface {

    private Menulist theCommand;

    public AllHomeworks(Menulist newCommand){
        this.theCommand = newCommand;
    }

    @Override
    public void execute() throws Exception {
        this.theCommand.allHomeworksUI();
    }
}
