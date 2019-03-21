package UI;

import java.io.IOException;

public class UpdateDeadline implements CommandInterface {

    private Menulist theCommand;

    public UpdateDeadline(Menulist newCommand){
        this.theCommand = newCommand;
    }

    @Override
    public void execute() throws Exception {
        this.theCommand.updateDeadlineUI();
    }
}
