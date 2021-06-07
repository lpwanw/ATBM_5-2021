package client;

import GUI.Chathis;
import RSA.RSA;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigInteger;
import static ultil.JsonUtil.formatMessage;

import java.net.URI;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

public class Client {

    public static final String SERVER = "ws://localhost:8887/ws/chat";
    public static RSA rsa;
    public static Chathis gui;

    public static void main(String[] args) throws Exception {
        ClientManager client = ClientManager.createClient();
        String message;
        // connect to server
        gui = new Chathis();
        String user = gui.startUp();
        gui.getChatText().setText(gui.getTextMess()+"\nWelcome to Tiny Chat!");
        Session session = client.connectToServer(ClientEndpoint.class, new URI(SERVER));
        gui.getChatText().setText(gui.getTextMess()+"\nYou are logged in as: " + user);
        gui.getMess().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String message = gui.getMess().getText();
                    gui.getMess().setText("");
                    gui.getChatText().setText(gui.getTextMess()+"\n"+message);
                    if (message.length() > 0) {
                        try {
                            session.getBasicRemote().sendText(formatMessage(rsa.encrypt(message), rsa.encrypt(user)));
                        } catch (IOException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        gui.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = gui.getMess().getText();
                gui.getMess().setText("");
                gui.getChatText().setText(gui.getTextMess()+"\n"+message);
                    if (message.length() > 0) {
                        try {
                            session.getBasicRemote().sendText(formatMessage(rsa.encrypt(message), rsa.encrypt(user)));
                        } catch (IOException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            }
        });
        // repeatedly read a message and send it to the server (until quit)
//        do {
//            message = scanner.nextLine();
//            if(message.length()>0)
//            session.getBasicRemote().sendText(formatMessage(rsa.encrypt(message), rsa.encrypt(user)));
//        } while (!message.equalsIgnoreCase("quit"));
    }

}
