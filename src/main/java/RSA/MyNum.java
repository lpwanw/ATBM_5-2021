/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSA;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 *
 * @author Vind
 */
public class MyNum {

    int[] n;

    public MyNum(String mess) {
        byte[] input = mess.getBytes();
        n = new int[input.length];
        for(int i = 0 ; i< input.length ; i++){
            n[i] = input[i]-64;
        }
    }
    public MyNum(byte[] input){
        n = new int[input.length];
        for(int i = 0 ; i< input.length ; i++){
            n[i] = input[i]-64;
        }
    }
    public MyNum(int[] input){
        n = new int[input.length];
        for(int i = 0 ; i< input.length ; i++){
            n[i] = input[i];
        }   
    }
    public MyNum modPow(int a, int b) {
        int[] result = new int[n.length];
        for(int i=0;i<n.length;i++){
            int value = (int) n[i];
            Integer integer = new Integer(value);
            BigInteger big = BigInteger.valueOf(integer);
            big = big.pow(a);
            Integer integer1 = new Integer(b);
            BigInteger bb = BigInteger.valueOf(integer1);
            big = big.mod(bb);
            result[i] = big.intValue();
        }
        return new MyNum(result);
    }
    // for one byte only
    public String toString(){
        int[] k = new int[n.length];
        String re = "";
        for(int i = 0 ; i< n.length ; i++){
            k[i] = (int) n[i] + 64;
            re+= (char) new Integer(k[i]).byteValue();
        }        
        return re;
    }
    public static void main(String[] args) {
        MyNum a = new MyNum("QuyenAnC");
        MyNum en = a.modPow(3, 1151119);
        MyNum de = en.modPow(765867, 1151119);
        System.out.println(a.toString());
        System.out.println(en.toString());
        System.out.println(de.toString());        
    }
}
