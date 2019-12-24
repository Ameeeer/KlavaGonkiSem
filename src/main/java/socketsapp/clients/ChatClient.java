package socketsapp.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sem.frames.GameFrame;
import org.postgresql.util.ReaderInputStream;
import socketsapp.models.User;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ChatClient {
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String jsonUser;
    private ObjectMapper mapper = new ObjectMapper();
    private boolean gameStarted = false;
    private JProgressBar oppoNentProgressBar;
    private JProgressBar userProgressBar;
    private String userName = "";

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            oppoNentProgressBar = new JProgressBar();
            userProgressBar = new JProgressBar();
            new Thread(receiveMessagesTask).start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String createJSON(String login, String password) {
        User user = User.newBuilder().setLogin(login).setPassword(password).build();
        try {
            userName = login;
            String jsonCreate = mapper.writeValueAsString(user);
            System.out.println(jsonCreate);
            jsonUser = jsonCreate;
            return jsonCreate;
        } catch (JsonProcessingException e) {
            // TODO: 12.11.2019 Add exception on create json value
            return null;
        }
    }

    public String jsonMessage(String login, String message) {
        User user = User.newBuilder().setLogin(login).setMessage(message).build();
        try {
            String jsonCreate = mapper.writeValueAsString(user);
            jsonUser = jsonCreate;
            return jsonUser;
        } catch (JsonProcessingException e) {
            // TODO: 12.11.2019 Add exception on create json value
            return null;
        }
    }

    public void sendMessage(String message) {
        switch (message) {
            case (""): {
                System.out.println("EmptyMess");
                break;
            }
            case ("/startGame"): {

            }
        }
        if (message.trim().equals("")) {
            System.out.println("Empty mess");
        } else {
            writer.println(message);
        }
    }

    private final Runnable receiveMessagesTask = new Runnable() {
        public void run() {
//            try {
//                User user = mapper.readValue(reader.readLine(), User.class);
//            } catch (IOException e) {
//                e.printStackTrace();
            while (true) {
                try {
                    String message = reader.readLine();
                    if (message.trim().length() < 1) {
                        message = "null";
                        System.exit(0);
                    }
                    System.out.println(message);
                    if (message.contains("hhhhh") && !message.contains(userName)) {
//                        setOppoNentProgressBarValue((int) Integer.parseInt(message.trim().split(".")[0]));
                        String[] strings = message.split("@");
                        setOppoNentProgressBarValue(Integer.parseInt(strings[1]));
                    } else if (!message.contains("hhhhh")){
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    };

    public int getOppoNentProgressBarVal() {
        return this.oppoNentProgressBar.getValue();
    }

    public void setOppoNentProgressBarValue(int x) {
        this.oppoNentProgressBar.setValue(x);
    }

    public int getUserProgressBarVal() {
        return this.userProgressBar.getValue();
    }

    public void setUserProgressBarVal(int x) {
        this.userProgressBar.setValue(x);
    }

    public String getUserName() {
        return userName;
    }
}









