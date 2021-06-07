/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSA;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vind
 */
public class RSAint {
    private BigInteger n,d,e;
    public static int main()
    {
    int prime;
        while (true)
        {
            int count = 0;
            double x  = Math.random();
            double y  = 10000 * x;
            double z  = Math.ceil(y);
            prime     = (int)z;
            for (int i = 1; i <= prime; i++)
            {
                int modfactor = prime % i;
                if (modfactor == 0)
                {
                    count++;
                }
            }
            if (count == 2)
            {
                break;
            }
        }
        return prime;
    }
    public static void main(String[] argv){
        BigInteger n,d,e,p,q;
        p = BigInteger.valueOf(main());
        q = BigInteger.valueOf(main());
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));
        e = BigInteger.ONE;
        boolean found = false;
        do {
            e = e.add(BigInteger.ONE);
            if (m.gcd(e).equals(BigInteger.ONE) && e.compareTo(m) < 0) {
                found = true;
            }
        } while (!found);
        d = e.modInverse(m);
        System.out.println(e+" "+d+" "+n);
    }
}
