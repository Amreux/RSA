import java.math.BigInteger;

public class RSA {

    private long prime1;
    private long prime2;
    private long modulus;
    private long publicKey;
    private long privateKey;

    public static boolean isPrime(long n) {
        if (n <= 1)
            return false;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public void setPrime1(long p1) {
            prime1 = p1;

    }

    public void setPrime2(long p2) {

            prime2 = p2;
    }

    public boolean setPublicKey(long e) {
        long phi= (prime1 - 1) * (prime2 - 1);
        if (e >= 1 && e<phi  &&GCD.gcd(phi,e)==1) {
            publicKey = e;
            return true;
        } else {
            return false;
        }
    }

    public long getPublicKey() {
        return publicKey;
    }

    public long getPrime1() {
        return prime1;
    }

    public long getPrime2() {
        return prime2;
    }

    public long getModulus() {
        return modulus;
    }

    public long getPrivateKey() {
        return privateKey;
    }


    public static class GCD
    {
        private GCD(){}
       public static long x=0;
        public static long y=0;
        public static long gcd(long a,long b)
        {
            long temp;
            if(a<b)
            {
                temp=a;
                a=b;
                b=temp;
            }
            if(b==0) {
                x=1;
                y=0;
                return a;
            }
            long res=gcd(b,a%b);
            temp=x;
            x=y;
            y=temp-(a/b)*x;
            return res;
        }
    }
    private static long modularInverse(long A, long B )
    {
        long a=A,b=B;


        long q=1;
        long X0=1;
        long X1=0;
        int i=0;
        while((b!=0))
        {
            long temp=b;
            if(i!=0)
            {
                long X2=X0-((X1*q));
                X0=X1; X1=X2;
            }
            i++;
            q=a/b;
            b=a%b; a=temp;
        }
        while (X1<0)
            X1= X1+B;
        return X1;
    }

    public boolean doesProductExceedLong(long num1,long num2)
    {
        BigInteger product = BigInteger.valueOf(num1).multiply(BigInteger.valueOf(num2));

        long maxLong = Long.MAX_VALUE;
        long minLong = Long.MIN_VALUE;

        BigInteger maxLongValue = BigInteger.valueOf(maxLong);
        BigInteger minLongValue = BigInteger.valueOf(minLong);

        return product.compareTo(maxLongValue) > 0 || product.compareTo(minLongValue) < 0;
    }

    public void generateKeys() {
        modulus = prime1 * prime2;
        long phi = (prime1 - 1) * (prime2 - 1);

        privateKey = modularInverse(publicKey, phi);
    }



    public static BigInteger mod(BigInteger m, BigInteger exponent, BigInteger n)
    {
        BigInteger tempE=exponent;
        BigInteger finalR=BigInteger.ONE;
        while(tempE.compareTo(BigInteger.ZERO) > 0)
        {
            if(tempE.mod(BigInteger.TWO).equals(BigInteger.ONE)) finalR=(m.multiply(finalR)).mod(n);
            m=m.multiply(m).mod(n);
            tempE=tempE.divide(BigInteger.TWO);
        }
        return finalR;
    }

    public String Encrypt(String message, long key, long mod) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            int c = message.charAt(i);
            long encryptedC = mod(BigInteger.valueOf(c), BigInteger.valueOf(key), BigInteger.valueOf(mod)).longValue();
            encryptedMessage.append(encryptedC).append("-");
        }
        return encryptedMessage.toString();
    }

    public String Decrypt(String message, long key, long mod) {
        String decryptedMessage = "";

        String[] encryptedMessage = message.split("-");
        for (String s : encryptedMessage) {
            long c = Long.parseLong(s);
            long decryptedC = mod(BigInteger.valueOf(c), BigInteger.valueOf(key), BigInteger.valueOf(mod)).longValue();
            decryptedMessage += (char) decryptedC;
        }
        return decryptedMessage;

    }
    public static void main(String[] args)
    {
//        RSA rsa = new RSA();
//        rsa.setPrime1(19);
//        rsa.setPrime2(29);
//        rsa.setPublicKey(13);
//        rsa.generateKeys();
//         String Message = "Wael ElGML 2000";
//        String encryptedMessage = rsa.Encrypt(Message, rsa.getPublicKey(), rsa.getModulus());
//
//       String decryptedMessage = rsa.Decrypt(encryptedMessage, rsa.getPrivateKey(), rsa.getModulus());
//        System.out.println("Message: " + Message);
//        System.out.println("Encrypted Message: " + encryptedMessage);
//        System.out.println("Decrypted Message: " + decryptedMessage);

        RSA rsa = new RSA();

        System.out.println(mod(BigInteger.valueOf(53), BigInteger.valueOf(17), BigInteger.valueOf(242599758489492419L)));


    }
}

