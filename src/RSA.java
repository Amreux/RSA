public class RSA {

    private int prime1;
    private int prime2;
    private int modulus;
    private int publicKey;
    private int privateKey;

    private boolean isPrime(int n) {
        if (n <= 1)
            return false;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public boolean setPrime1(int p1) {
        if (isPrime(p1)) {
            prime1 = p1;
            return true;
        } else {
            return false;
        }
    }

    public boolean setPrime2(int p2) {
        if (isPrime(p2)) {
            prime2 = p2;
            return true;
        } else {
            return false;
        }
    }

    public boolean setPublicKey(int e) {
        if (e >= 1 && e < (prime1 - 1) * (prime2 - 1)) {
            publicKey = e;
            return true;
        } else {
            return false;
        }
    }

    public int getPublicKey() {
        return publicKey;
    }

    public int getPrime1() {
        return prime1;
    }

    public int getPrime2() {
        return prime2;
    }

    public int getModulus() {
        return modulus;
    }

    public int getPrivateKey() {
        return privateKey;
    }

    public void generateKeys() {
        modulus = prime1 * prime2;
        int phi = (prime1 - 1) * (prime2 - 1);
        int d = 1;
        while ((d * publicKey) % phi != 1) {
            d++;
        }
        privateKey = d;
    }

    public int modularExponentiation(int base, int exponent, int modulus) {
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent = exponent / 2;
        }
        return result;
    }

    public String Encrypt(String message, int key, int mod) {
        String encryptedMessage = "";
        for (int i = 0; i < message.length(); i++) {
            int c = message.charAt(i);
            int encryptedC = modularExponentiation(c, key, mod);
            encryptedMessage +=  (char)encryptedC;
        }
        return encryptedMessage;
    }

    public String Decrypt(String message, int key, int mod) {
        String decryptedMessage = "";

        for (int i = 0; i < message.length(); i++) {
            int decryptedC = modularExponentiation((int)message.charAt(i), key, mod);
            decryptedMessage += (char) decryptedC;

        }
        return decryptedMessage;

    }
    public static void main(String[] args)
    {
        RSA rsa = new RSA();
        rsa.setPrime1(19);
        rsa.setPrime2(29);
        rsa.setPublicKey(13);
        rsa.generateKeys();
         String Message = "Wael ElGML 2000";
        String encryptedMessage = rsa.Encrypt(Message, rsa.getPublicKey(), rsa.getModulus());

       String decryptedMessage = rsa.Decrypt(encryptedMessage, rsa.getPrivateKey(), rsa.getModulus());
        System.out.println("Message: " + Message);
        System.out.println("Encrypted Message: " + encryptedMessage);
        System.out.println("Decrypted Message: " + decryptedMessage);

    }
}

