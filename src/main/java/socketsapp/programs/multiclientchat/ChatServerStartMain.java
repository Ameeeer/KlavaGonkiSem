package socketsapp.programs.multiclientchat;


import socketsapp.servers.MultiClientServer;

public class ChatServerStartMain {
    public static void main(String[] args) {
        MultiClientServer multiClientServer =
                new MultiClientServer();
        int port = 8888;
        String serverAddress = "localhost";
        multiClientServer.start(port,serverAddress);
    }
}
