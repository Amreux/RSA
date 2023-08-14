import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RSACrack {


    public static void main(String[]args) throws FileNotFoundException {
        File primesFile = new File("PrimeNumbers/primes.txt");
        while (true)
        {
        Scanner fileScanner = new Scanner(primesFile);
        Scanner inputScanner = new Scanner(System.in);
            System.out.println("Please Entire the modulus to factor and the chosen public key:");
            long mod = inputScanner.nextLong();
            long publicKey = inputScanner.nextLong();
            long prime1 = 1, prime2 = 1;
            long startTime = System.nanoTime();
            while (fileScanner.hasNext()) {
                prime1 = fileScanner.nextLong();
                if (mod % prime1 == 0) {
                    prime2 = mod / prime1;
                    break;
                }
            }

            if (prime2 == 1) {
                prime1++;
                while (true) {
                    if (!RSA.isPrime(prime1)) {
                        prime1++;
                        continue;
                    }
                    if (mod % prime1 == 0) {
                        prime2 = mod / prime1;
                        break;
                    }
                }
            }
            long phi = (prime1 - 1) * (prime2 - 1);
            RSA.GCD.gcd(phi, publicKey);
            System.out.println("Cracking complete, private key: " + ((RSA.GCD.y > 0) ? RSA.GCD.y : phi + RSA.GCD.y));
            System.out.println("p :"+prime1+" q: "+prime2);
            System.out.println("Elapsed Time: " + (System.nanoTime() - startTime) / 1000000 + " ms");
        }
    }
}
