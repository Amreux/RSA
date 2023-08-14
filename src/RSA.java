public class RSA {

    private long prime1;
    private long prime2;
    private long modulus;
    private long publicKey;
    private long privateKey;

    private boolean isPrime(long n) {
        if (n <= 1)
            return false;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public boolean setPrime1(long p1) {
        if (isPrime(p1)) {
            prime1 = p1;
            return true;
        } else {
            return false;
        }
    }

    public boolean setPrime2(long p2) {
        if (isPrime(p2)) {
            prime2 = p2;
            return true;
        } else {
            return false;
        }
    }

    public boolean setPublicKey(long e) {
        if (e >= 1 && e < (prime1 - 1) * (prime2 - 1)) {
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


    private class GCD
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


    public void generateKeys() {
        modulus = prime1 * prime2;
        long phi = (prime1 - 1) * (prime2 - 1);
        GCD.gcd(publicKey,phi);
        privateKey = GCD.x;

    }

    public long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent = exponent / 2;
        }
        return result;
    }

    public String Encrypt(String message, long key, long mod) {
        String encryptedMessage = "";
        for (int i = 0; i < message.length(); i++) {
            int c = message.charAt(i);
            long encryptedC = modularExponentiation(c, key, mod);
            encryptedMessage +=  (char)encryptedC;
        }
        return encryptedMessage;
    }

    public String Decrypt(String message, long key, long mod) {
        String decryptedMessage = "";

        for (int i = 0; i < message.length(); i++) {
            long decryptedC = modularExponentiation((int)message.charAt(i), key, mod);
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
    }
}

