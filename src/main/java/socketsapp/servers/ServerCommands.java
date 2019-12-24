package socketsapp.servers;

public class ServerCommands {
    private static ServerCommands commands = null;

    public ServerCommands() {
        super();
        commands = this;
    }

    public static ServerCommands getInstance() {
        if (commands==null){
            synchronized (ServerCommands.class){
                if (commands == null){
                    commands = new ServerCommands();
                }
            }
        }
        return commands;
    }
}
