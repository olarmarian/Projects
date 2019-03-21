package UI;

public class MenuInvoker {
    CommandInterface theCommand;

    public MenuInvoker(CommandInterface newCommand){
        this.theCommand = newCommand;
    }

    public void executeCommand() throws Exception {
        this.theCommand.execute();
    }
}
