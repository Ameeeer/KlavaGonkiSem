package socketsapp.servers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import socketsapp.models.User;
import socketsapp.repositories.CrudImpl;
import socketsapp.repositories.DBService;
import socketsapp.services.AuthService;
import socketsapp.services.AuthServiceInteface;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MultiClientServer {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private int freeIndex = 0;
    private int freeIndexToConnect;

    public MultiClientServer() {
        clients = new ArrayList<ClientHandler>();
    }

    public void start(int port, String ipAddressServer) {
        try {
            serverSocket = new ServerSocket(port, 10);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler handler =
                        new ClientHandler(socket);
                handler.start();
            } catch (SocketTimeoutException exception) {

            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        private Long clientId;
        private String clientName;
        private Socket clientSocket;
        private BufferedReader reader;
        private int handlerId = 0;
        private JProgressBar userProgress;
        private JProgressBar opponentProgressBar;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            clients.add(this);
        }


        @Override
        public void run() {
            try {
                AuthServiceInteface auth = new AuthService();
                ObjectMapper mapper = new ObjectMapper();
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                String jsonOnConnect = reader.readLine();
                User user = mapper.readValue(jsonOnConnect, User.class);
                String login = user.getLogin();
                DBService crudInterface = new CrudImpl();
                Optional<User> optionalUser = crudInterface.readUser(user);
                if (optionalUser.isPresent()) {
                    System.out.println("found");
                    this.clientName = login;
                    clientId = optionalUser.get().getId();
                    printWriter.println("Вы подключены");
                    System.out.println("Подключился пользователь : " + clientName);
                } else if (crudInterface.saveUser(user)) {
                    System.out.println("notfound");
                    this.clientName = login;
                    Optional<User> newUser = crudInterface.readUser(user);
                    clientId = newUser.get().getId();
                    printWriter.println("Вы зарегистрированы");
                    System.out.println("Зарегистрирован новый пользователь: " + clientName);
                } else {
                    printWriter.println("Логин или пароль введены неверно либо сущевствуют");
                    printWriter.println("Вы будете отключены");
                    clientSocket.close();
                    return;
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }


            String line;
            try {
                DBService crudInterface = new CrudImpl();
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    switch (line) {
                        case ("/end"): {
                            System.out.println("Пользователь отключился : " + clientName);
                            clients.remove(this);
                            reader.close();
                            clientSocket.close();
                            break;
                        }
//                        case ("/startGame"): {
//                            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
//                            userProgress = (JProgressBar) objectInputStream.readObject();
//                            for (ClientHandler opponent:clients) {
//                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(opponent.clientSocket.getOutputStream());
//                                objectOutputStream.writeObject(userProgress);
//                                objectOutputStream.flush();
//                            }
//                        }
                        case ("stopGame"): {

                        }
                    }
//                    crudInterface.saveMessageWithId(clientId, line);
                    for (ClientHandler client : clients) {
                        PrintWriter writer = new PrintWriter(
                                client.clientSocket.getOutputStream(), true);
                        writer.println("[" + clientName + "]" + " " + line);
                        writer.println(line);
                        writer.flush();
                    }

                }
                System.out.println("Пользователь откл : " + clientName);

                clients.remove(this);
                clientSocket.close();
                reader.close();
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }
    }
}
