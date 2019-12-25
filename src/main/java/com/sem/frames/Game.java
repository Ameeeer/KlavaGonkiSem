package com.sem.frames;

import com.sem.logic.KeyLogic;
import keeptoo.KButton;
import keeptoo.KGradientPanel;
import socketsapp.clients.ChatClient;
import socketsapp.models.User;
import com.sem.logic.KeyLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import keeptoo.KButton;
import keeptoo.KTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;

public class Game {

    private boolean isOkWord = false;
    private JProgressBar progressBar;
    private JProgressBar playerProgressBar;
    private static ChatClient chatClient;
    private String curMes;
    private JTextArea chatText;
    private JTextField chat;
    final JFrame jFrame = getMenuFrame();

    public JFrame getjFrame() {
        return jFrame;
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Game() {
        JPanel jPanel = new JPanel();
        KGradientPanel consolePanel = new KGradientPanel();
        consolePanel.kStartColor = Color.GREEN;
        consolePanel.kEndColor = Color.BLUE;
        JMenuBar jMenuBar = new JMenuBar();
        jPanel.setLayout(new BorderLayout());

        final JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        KeyLogic keysLogic = new KeyLogic();
        if (!keysLogic.text.isEmpty()) {
            textArea.setText(keysLogic.text);
            textArea.setEnabled(false);
            textArea.setDisabledTextColor(Color.BLACK);
        } else {
            textArea.setText("Не удалось загрузить текст. Печатайте это на скорость.");
        }

        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setBackground(new Color(223, 220, 250));
        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jPanel.add(jScrollPane, BorderLayout.CENTER);


        JMenu file = new JMenu("File");
        jMenuBar.add(file);

        JMenuItem Exit = file.add(new JMenuItem("Exit"));

        Exit.addActionListener(e -> System.exit(0));
        Exit.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));

        chat = new JTextField(10);
        KButton jbSendMessage = new KButton();
        jbSendMessage.setText("Отправить");
        jbSendMessage.kBackGroundColor = Color.blue;
        jbSendMessage.setBackground(new Color(103, 94, 191));

        KGradientPanel chatPanel = new KGradientPanel();
        chatPanel.kStartColor = Color.GREEN;
        chatPanel.kEndColor = Color.BLUE;
        Box chatBox = Box.createHorizontalBox();
        chatBox.add(chat);
        chatBox.add(jbSendMessage);
        KButton exitButton = new KButton();
        exitButton.setText("Выйти");
        exitButton.setFont(new Font("TimesRoman", Font.BOLD, 20));
        exitButton.kBackGroundColor = Color.RED;
//        exitButton.setBackground(new Color(224, 56, 56));
        exitButton.kAllowGradient = true;
        exitButton.kBackGroundColor = Color.GREEN;
        exitButton.kHoverColor = Color.RED;
        exitButton.setPreferredSize(new Dimension(80, 100));

        exitButton.addActionListener((ActionEvent e) -> {
            int input = JOptionPane.showConfirmDialog(jFrame, "Уерены?");
            if (input == 0) {
                System.exit(0);
            }
        });


        chatText = new JTextArea(30, 10);
        chatText.setLineWrap(true);
        chatText.setWrapStyleWord(true);
        chatText.setBackground(new Color(223, 220, 250));
        JScrollPane chatScroll = new JScrollPane(chatText);
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel text = new JLabel("Start chatting with gamers");

        chatText.setEnabled(false);
        chatText.setDisabledTextColor(Color.BLACK);
        chatText.setText("Привет " + chatClient.getUserName());
        jbSendMessage.addActionListener((ActionEvent e) -> {// TODO: 25.12.2019 Chat
            String mes = chat.getText();
            if (!mes.equals("")) {
                chatClient.sendMessage(chat.getText());
                chat.setText("");
//                chatText.setText(curMes);
                System.out.println(curMes);
            }
        });

        text.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        Box textBox = Box.createHorizontalBox();
        textBox.add(text);
        Box exitBox = Box.createHorizontalBox();
        exitBox.add(exitButton);
        chatPanel.setBackground(Color.WHITE);
        chatPanel.add(textBox, BorderLayout.NORTH);
        chatPanel.add(chatScroll);
        chatPanel.add(chatBox);
        chatPanel.add(exitBox);


        JTextArea console = new JTextArea(15, 100);
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        JScrollPane consoleScroll = new JScrollPane(console);
        console.setBackground(new Color(223, 220, 250));
        consoleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        consolePanel.setBackground(Color.WHITE);
        consolePanel.add(consoleScroll);

        JButton message = new JButton("Отправить ответ");
        message.setBackground(new Color(103, 94, 191));
        message.addActionListener((ActionEvent e) -> {
            if (console.getText().equals(keysLogic.text)) {
                JOptionPane.showMessageDialog(jFrame, "Вы победили");

            }
        });

        Box consoleBox = Box.createHorizontalBox();
        consoleBox.add(consoleScroll);
        consoleBox.add(message);

        progressBar = new JProgressBar();
        JLabel progressText = new JLabel("Progress opponent ");
        progressText.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        progressBar.setStringPainted(true);
        progressBar.setMaximum(0);
        progressBar.setMaximum(100);
        Box progressBox = Box.createHorizontalBox();
        progressBox.add(progressText);
        progressBox.add(progressBar);

        playerProgressBar = new JProgressBar();
        JLabel playerProgressText = new JLabel("Your progress        ");
        playerProgressText.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        playerProgressBar.setStringPainted(true);
        playerProgressBar.setMaximum(0);
        playerProgressBar.setMaximum(100);
        Box playerProgressBox = Box.createHorizontalBox();
        playerProgressBox.add(playerProgressText);
        playerProgressBox.add(playerProgressBar);

        consolePanel.setLayout(new BoxLayout(consolePanel, BoxLayout.Y_AXIS));
        consolePanel.add(consoleBox);
        consolePanel.add(playerProgressBox);
        consolePanel.add(progressBox);
//        consolePanel.setBorder(BorderFactory);
        consolePanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 40, 3));

        String[] textArr = keysLogic.text.split("");
        console.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() >= 32 & e.getKeyCode() <= 126 || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    int currentTextLength = console.getText().length();
                    String currentTextString = "";
                    for (int i = 0; i < currentTextLength; i++) {
                        currentTextString += textArr[i];
                    }
                    if (console.getText().equals(currentTextString)) {
                        isOkWord = true;
                        console.setForeground(Color.BLACK);
                        if (console.getText().equals(keysLogic.text)) {
                            JOptionPane.showMessageDialog(jFrame, "Игра закончена");
                        }
                    } else {
                        isOkWord = false;
                        console.setForeground(Color.RED);
                    }
                    if (isOkWord) {
                        double progressCountDouble = (double) currentTextLength / keysLogic.text.length();
                        double progressCount1Double = progressCountDouble * 100;
                        playerProgressBar.setValue((int) progressCount1Double);
                    }
                }
            }
        });

        jFrame.setJMenuBar(jMenuBar);

        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));

        jPanel.add(consolePanel, BorderLayout.SOUTH);
        jPanel.add(chatPanel, BorderLayout.EAST);
        jFrame.add(jPanel);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        jFrame.revalidate();
        jFrame.setVisible(true);
        new Thread(() -> {
            while (true) {
                try {
                    if (!chatClient.getUserName().equals("")) {
                        chatClient.sendMessage(chatClient.getUserName() + "/hhhhh@" + getProgressBar().getValue());
                        setOpponentBarValue(chatClient.getOppoNentProgressBarVal());
                        Thread.sleep(1000);
                    } else {
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader bufferedReader = chatClient.getReader();
                while (true) {
                    try {
                        String message = bufferedReader.readLine();
                        if (message.trim().length() < 1) {
                            message = "null";
                        }
                        if (message.contains("hhhhh") && !message.contains(chatClient.getUserName())) {
//                        setOppoNentProgressBarValue((int) Integer.parseInt(message.trim().split(".")[0]));
                        } else if (!message.contains("hhhhh") && !message.equals("null")) {
//                            curMes = chatText.getText() + "\n" + message;
                            chatText.append("\n" + message + "\n");
                        }
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }).start();
//            InGameProcess inGameProcess = new InGameProcess();
//            inGameProcess.start();
    }

    public JProgressBar getProgressBar() {
        return playerProgressBar;
    }

    public void setOpponentBarValue(int value) {
        this.progressBar.setValue(value);
    }

    private JFrame getMenuFrame() {
        JFrame jFrame = new JFrame() {
        };
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width / 2 - 400, dimension.height / 2 - 300, 800, 600);
        return jFrame;
    }


    public static class RegistrationFrame {
        private JTextField login;
        private JPasswordField password;
        private String stringPassword;

        public RegistrationFrame() {
            JFrame frame = getFrame();
            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(new Color(255, 255, 255, 0));

            JLabel text = new JLabel("Registration");
            text.setFont(new Font("TimesRoman", Font.BOLD, 25));
            text.setPreferredSize(new Dimension(50, 50));

            JLabel loginText = new JLabel("Login ");
            loginText.setFont(new Font("TimesRoman", Font.PLAIN, 20));

            JLabel passwordText = new JLabel("Password");
            passwordText.setFont(new Font("TimesRoman", Font.PLAIN, 20));

            login = new JTextField(20);
            login.setFont(new Font("TimesRoman", Font.PLAIN, 20));

            password = new JPasswordField(20);
            password.setFont(new Font("TimesRoman", Font.PLAIN, 20));

            KButton start = new KButton();
            start.setText("GO!");
            start.kBackGroundColor = Color.BLUE;
            start.setPreferredSize(new Dimension(75, 75));
            start.setFont(new Font("TimesRoman", Font.BOLD, 20));
            start.setSize(start.getPreferredSize());
            start.setBounds(start.getX(), start.getY(), start.getWidth(), start.getHeight());

            start.addActionListener((ActionEvent e) -> {
                stringPassword = new String(password.getPassword());
                System.out.println(stringPassword);
                if (!login.getText().equals("") && !stringPassword.equals("")) {
                    String serverIpAddress = "localhost";
                    int port = 8888;
                    chatClient = new ChatClient();
                    String userConnect = chatClient.createJSON(login.getText(), stringPassword);
                    chatClient.startConnection(serverIpAddress, port);
                    System.out.println(userConnect);
                    chatClient.sendMessage(userConnect);
                    this.getFrame().setVisible(false);
//                    chatClient = chatClient;
//                    this.getFrame().setVisible(false);
                    Game game = new Game();
                    game.setChatClient(chatClient);
                } else {
                    JOptionPane.showMessageDialog(frame, "Не оставляйте поля пустыми");
                }
            });


            Box bText = Box.createHorizontalBox();
            bText.add(text);
            Box logText = Box.createHorizontalBox();
            logText.add(loginText);
            Box log = Box.createHorizontalBox();
            log.add(login);
            Box pass = Box.createHorizontalBox();
            pass.add(password);
            Box passText = Box.createHorizontalBox();
            passText.add(passwordText);
            Box button = Box.createHorizontalBox();
            button.add(start);
            Box box = Box.createVerticalBox();
            box.add(Box.createRigidArea(new Dimension(10, 0)));
            box.add(bText);
            box.add(logText);
            box.add(log);
            box.add(passText);
            box.add(pass);
            box.add(button);
            mainPanel.add(box);

//            frame.setContentPane(new PaintImage("c.jpg"));
            Container container = frame.getContentPane();
            container.add(mainPanel);
            frame.setVisible(true);
        }

        private JFrame getFrame() {
            JFrame jFrame = new JFrame() {
            };
            jFrame.setVisible(true);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setResizable(false);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            jFrame.setBounds(dimension.width / 2 - 250, dimension.height / 2 - 150, 500, 300);
            return jFrame;
        }

    }


    public static class PaintImage extends JPanel {
        private Image backImg;

        public PaintImage(String fileName) {
            try {
                backImg = ImageIO.read(this.getClass().getResource(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backImg, 0, 0, this);
        }
    }
}