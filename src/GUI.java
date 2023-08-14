import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI  {

    private RSA rsa;
   private JTextField receiveField;
private Client client;
    private  enum ErrorType {
        PRIME1,
        PRIME2,
        PUBLICKEY,
        EMPTY,
        NONNUMERIC,
        MESSAGEGREATERTHANMODULUS
    }
    private   List<String>  errorList = new ArrayList<>();
    private  void addError(ErrorType errorType) {
        switch (errorType) {
            case PRIME1 -> errorList.add("Prime 1 must be a prime number");
            case PRIME2 -> errorList.add("Prime 2 must be a prime number");
            case PUBLICKEY -> errorList.add("Public key must be an integer between 1 and (prime1-1)*(prime2-1)");
            case EMPTY -> errorList.add("Please fill in all fields");
            case NONNUMERIC -> errorList.add("Please enter a numeric value");
            case MESSAGEGREATERTHANMODULUS -> errorList.add("Message must be less than modulus");
        }
    }

    public GUI(RSA rsa)
    {
        this.rsa = rsa;
        ShowKeyGenerationWindow();

    }

    public  void ShowKeyGenerationWindow(){
        JFrame frame = new JFrame("RSA");
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        //GUI Components
        JLabel mainLabel = new JLabel("Key Generator");
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.TOP);
        mainLabel.setFont(new Font("Verdana", Font.PLAIN, 40));
        panel.add(mainLabel);
        JLabel label1 = new JLabel("Please choose two sufficiently large prime numbers: ");
        panel.add(label1);
        JTextField prime1field = new JTextField();
        JTextField prime2field = new JTextField();
        JPanel panel2 = new JPanel(new GridLayout(0, 2));
        JLabel prime1Label = new JLabel("Prime 1: ");
        JLabel prime2Label = new JLabel("Prime 2: ");
        panel2.add(prime1Label);
        panel2.add(prime2Label);
        panel2.add(prime1field);
        panel2.add(prime2field);
        panel2.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.add(panel2);
        JPanel panel3 = new JPanel(new GridLayout(0, 1));
       JLabel label2 = new JLabel("Please choose an integer e as your public key:" );
       panel3.add(label2);
       JTextField eField = new JTextField();
       panel3.add(eField);
       panel.add(panel3);



        JPanel panel4 = new JPanel(new GridLayout(3, 1));
        JLabel label3 = new JLabel("Your public key is:  "+rsa.getPublicKey());
        panel4.add(label3);
        JLabel label4 = new JLabel("Your private key is: "+rsa.getPrivateKey());
        panel4.add(label4);
        JLabel label5 = new JLabel("Your modulus is:     "+rsa.getModulus());
        panel4.add(label5);
        panel4.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(panel4);
        panel4.setVisible(false);


        JButton button = new JButton("Generate Private Key");
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        button.addActionListener(e->{
            if(!rsa.setPrime1(Long.parseLong(prime1field.getText()))) {
                addError(ErrorType.PRIME1);
                System.out.println("prime1 is not prime");
            }
            if(!rsa.setPrime2(Long.parseLong(prime2field.getText())))
                addError(ErrorType.PRIME2);
            if(!rsa.setPublicKey(Long.parseLong(eField.getText())))
                addError(ErrorType.PUBLICKEY);
            if(prime1field.getText().equals("") || prime2field.getText().equals("") || eField.getText().equals(""))
                addError(ErrorType.EMPTY);
            if(!prime1field.getText().matches("[0-9]+") || !prime2field.getText().matches("[0-9]+") || !eField.getText().matches("[0-9]+"))
                addError(ErrorType.NONNUMERIC);
            if(errorList.size()>0)
            {
                String errorString = "";
                for(String error : errorList)
                {
                    errorString += error + "\n";
                }
                JOptionPane.showMessageDialog(frame, errorString);
                errorList.clear();
            }
            else {
                rsa.generateKeys();
                button.setText("Successfully Generated!");
                panel4.setVisible(true);
                label3.setText("Your public key is:  " + rsa.getPublicKey());
                label4.setText("Your private key is: " + rsa.getPrivateKey());
                label5.setText("Your modulus is:     " + rsa.getModulus());
                this.ShowChatWindow();

            }

        });



        panel.add(button);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public void ShowChatWindow()
    {
        JFrame frame = new JFrame("Chat");
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        //GUI Components
        JPanel sendPanel = new JPanel(new GridLayout(0, 1));
        JLabel sendLabel = new JLabel("Send Message");
        JTextField sendField = new JTextField(50);
        JLabel encryptionKeyLabel = new JLabel("Encryption Key");
        JTextField encryptionKeyField = new JTextField(50);
        JLabel modulusLabel = new JLabel("Modulus");
        JTextField modulusField = new JTextField(50);
        JLabel sentMessageLabel = new JLabel("Sent Message: ");
        JTextField sentMessage = new JTextField(50);
        sentMessage.setEditable(false);
        JButton sendButton = new JButton("Encrypt and Send");
        sendButton.addActionListener(e->{
            if(sendField.getText().equals("") || encryptionKeyField.getText().equals("") || modulusField.getText().equals(""))
                addError(ErrorType.EMPTY);
            if(!sendField.getText().matches("[0-9]+") || !encryptionKeyField.getText().matches("[0-9]+") || !modulusField.getText().matches("[0-9]+"))
                addError(ErrorType.NONNUMERIC);
            for(int i = 0; i < sendField.getText().length(); i++)
            {
                if((long)sendField.getText().charAt(i) > rsa.getModulus())
                {
                    addError(ErrorType.MESSAGEGREATERTHANMODULUS);
                    break;
                }
            }
            if(errorList.size()>0)
            {
                String errorString = "";
                for(String error : errorList)
                {
                    errorString += error + "\n";
                }
                JOptionPane.showMessageDialog(frame, errorString);
                errorList.clear();
            }else {

                String message = sendField.getText();
                String encryptionKey = encryptionKeyField.getText();
                String encryptedMessage = rsa.Encrypt(message, Long.parseLong(encryptionKey), Long.parseLong(modulusField.getText()));
                client.sendMessages(encryptedMessage);
                sentMessage.setText(encryptedMessage);
            }
        });
        sendPanel.add(sendLabel);
        sendPanel.add(sendField);
        sendPanel.add(encryptionKeyLabel);
        sendPanel.add(encryptionKeyField);
        sendPanel.add(modulusLabel);
        sendPanel.add(modulusField);
        sendPanel.add(sendButton);
        sendPanel.add(sentMessageLabel);
        sendPanel.add(sentMessage);
        sendPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.add(sendPanel);


        JPanel receivePanel = new JPanel(new GridLayout(0, 1));
        JLabel receiveLabel = new JLabel("Received Encrypted Message");
        receiveField = new JTextField(50);
        receiveField.setEditable(false);
        JLabel decryptionKeyLabel = new JLabel("Decryption Key");
        JTextField decryptionKeyField = new JTextField(50);
        JLabel modulusLabel2 = new JLabel("Modulus");
        JTextField modulusField2 = new JTextField(50);
        JLabel receivedMessageLabel = new JLabel("Received Decrypted Message: ");
        JTextField receivedMessage = new JTextField(50);
        receivedMessage.setEditable(false);
        JButton receiveButton = new JButton("Decrypt");
        receiveButton.addActionListener(e->{
            String message = receiveField.getText();
            String decryptionKey = decryptionKeyField.getText();
            String decryptedMessage = rsa.Decrypt(message,Long.parseLong(decryptionKey),Long.parseLong(modulusField2.getText()));
            receivedMessage.setText(decryptedMessage);
        });

        receivePanel.add(receiveLabel);
        receivePanel.add(receiveField);
        receivePanel.add(decryptionKeyLabel);
        receivePanel.add(decryptionKeyField);
        receivePanel.add(modulusLabel2);
        receivePanel.add(modulusField2);
        receivePanel.add(receiveButton);
        receivePanel.add(receivedMessageLabel);
        receivePanel.add(receivedMessage);
        receivePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.add(receivePanel);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }
    public void setReceived(String message)
    {
        receiveField.setText(message);
    }
    public void setClient(Client client)
    {
        this.client = client;
    }
    public static void main(String[] args) {

    }
}