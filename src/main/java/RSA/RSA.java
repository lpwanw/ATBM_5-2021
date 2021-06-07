package RSA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RSA {
    //d: private key, public key

    private BigInteger n, d, e;

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    /**
     * Create an instance that can encrypt using someone elses public key.
     */
    public RSA(BigInteger newn, BigInteger newe) {
        n = newn;
        e = newe;
    }
    public RSA(BigInteger newn, BigInteger newe,BigInteger newd) {
        n = newn;
        e = newe;
        d = newd;
    }
    /**
     * Create an instance that can both encrypt and decrypt.
     */
    public RSA(int bits) {
        KeyRSA(bits);
    }
    public RSA(){
        try{
            File myObj = new File("publickey.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                String[] data = myReader.nextLine().split("/");
                e = new BigInteger(data[0]);
                n = new BigInteger(data[1]);
            }
            myReader.close();
            myObj = new File("privatekey.txt");
            myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                String[] data = myReader.nextLine().split("/");
                d = new BigInteger(data[0]);
                n = new BigInteger(data[1]);
            }
            myReader.close();
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public void KeyRSA(int bits) {
        SecureRandom r = new SecureRandom();//create BigInteger r random
        BigInteger p = new BigInteger(bits, 100, r);
        BigInteger q = new BigInteger(bits, 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));
        boolean found = false;
        do {
            e = new BigInteger(bits, 50, r);
            if (m.gcd(e).equals(BigInteger.ONE) && e.compareTo(m) < 0) {
                found = true;
            }
        } while (!found);
        d = e.modInverse(m);
        try {
            PrintWriter out = new PrintWriter("publickey.txt");
            out.print(e+"/"+n);
            out.close();
            PrintWriter out1 = new PrintWriter("privatekey.txt");
            out1.print(d+"/"+n);
            out1.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    // Encrypt the given plaintext message.Use public key decrypt
    public synchronized String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }

    //Encrypt the given plaintext message.Use public key decrypt
    public synchronized BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    // Decrypt the given ciphertext message.Use private key decrypt
    public  synchronized String decrypt(String message) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }

    // Decrypt the given ciphertext message.Use private key decrypt
    public synchronized BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
    }
    public static void main(String[] args) {
        RSA rsa = new RSA(5);
        String text ="To la Huyen xinh dep, t rat quy ban Khiem nhung t lai co tinh cam voi anh Tien Quan cua t=))";
        String mahoa=rsa.encrypt(text);
        System.out.println("�?ây là kết quả đoạn mã hóa:"+mahoa);
        System.out.println("Giải mã được như sau: "+rsa.decrypt(mahoa));
        String msg = "wanw: abcd";
        String[] temp = msg.split(":");
        String data = temp[1].substring(1);
        System.out.println(data);
    }
}
