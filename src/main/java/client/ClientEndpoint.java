package client;

import static java.lang.String.format;

import model.Message;
import model.MessageDecoder;
import model.MessageEncoder;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.text.SimpleDateFormat;
import RSA.RSA;
import static client.Client.gui;
import server.Server;
import java.math.BigInteger;
@javax.websocket.ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ClientEndpoint {
    public BigInteger n,d,e;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(format("Connection established. session id: %s", session.getId()));
    }

    @OnMessage
    public void onMessage(Message message) {
        if(message.getSender().equals("key")){
            String[] input = message.getContent().split("/");
            try{
                d = new BigInteger(input[0]);
                e = new BigInteger(input[1]);
                n = new BigInteger(input[2]);
                Client.rsa = new RSA(n, e, d);
            }catch(Exception ex){
                
            }
        }else{
            System.out.println(format("[%s:%s] %s", simpleDateFormat.format(message.getReceived()), Client.rsa.decrypt(message.getSender()),Client.rsa.decrypt(message.getContent())));
            Client.gui.getChatText().setText(gui.getTextMess()+"\n"+format("[%s:%s] %s", simpleDateFormat.format(message.getReceived()), Client.rsa.decrypt(message.getSender()),Client.rsa.decrypt(message.getContent())));
        }
    }
}   
