package server;

import RSA.RSA;
import java.math.BigInteger;
import java.util.Scanner;
import javax.websocket.DeploymentException;

public class Server {
    public static BigInteger n,e,d;
    public static void main(String[] args) {

        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 8887, "/ws", ServerEndpoint.class);

        try {
            RSA rsa = new RSA(2048);
            n = rsa.getN();
            e = rsa.getE();
            d = rsa.getD();
            server.start();
            System.out.println("Press any key to stop the server..");
            new Scanner(System.in).nextLine();
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }

}
