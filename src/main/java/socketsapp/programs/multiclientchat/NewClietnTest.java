package socketsapp.programs.multiclientchat;

import socketsapp.clients.ChatClient;
import socketsapp.models.User;

import java.util.Scanner;

public class NewClietnTest {
    public static void main(String[] args) {
        String serverIpAddress = "localhost";
        int port = 8888;
        ChatClient chatClient = new ChatClient();
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите Логин");
        String login = sc.next().trim();
        System.out.println("Введите Пароль");
        String password = sc.next().trim();
        User user = User.newBuilder().build();
        String userConnect = chatClient.createJSON(login,password);
        chatClient.startConnection(serverIpAddress, port);
        System.out.println(userConnect);
        chatClient.sendMessage(userConnect);
        while (true) {
            String message = sc.nextLine();
            chatClient.sendMessage(message);
            if (message.equals("/end")) System.exit(0);
        }
    }
}
