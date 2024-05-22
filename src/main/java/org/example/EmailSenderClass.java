package org.example;
import jakarta.mail.*;
import java.util.Properties;
import jakarta.mail.Session;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import static jakarta.mail.internet.InternetAddress.*;

/**
 * Contains the info and methods that sends an email to the user when they book a tickets
 * This class follow the principle static utility class of no instances since no instance can be made
 * @author Joseph Josue Forestal
 */
public class EmailSenderClass {

    private static final String EMAIL_FROM = "holyboom4@gmail.com";
    private static String EMAIL_TO = "";
    private static final String APP_PASSWORD = "mrrh uijr ijox zupc";

    /**
     * No instance can be made from that class
     */
   private EmailSenderClass(){}

    /**
     *  Method check if email is valid
     * @param email the email
     * @return true if the email matches the regular expression or false if it doesn't.
     */
    public static boolean isEmailValid(String email){
            if(email.matches("[a-zA-Z0-9._%+-]+@gmail\\.com")){
            return  true;
            }else{
                System.out.println("Sorry :( we only work with gmail ");
                System.out.println("Invalid email format  --> na1meexample@gmail.com");
                return  false;
            }
    }

    /**
     * Send email to a recipient
     * @param recipient the one receiving the email
     * @param message the message or content of the email
     * @param subject the subject of the email
     */
    public static void sendEmail(String recipient, String message, String subject) throws MessagingException {
        try {
            String username = "holyboom4@gmail.com";
            EMAIL_TO = recipient;


            Message message1 = new MimeMessage(getEmailSession());
            message1.setFrom(new InternetAddress(EMAIL_FROM));
            message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
            message1.setSubject(subject);
            message1.setText(message);
            Transport.send(message1);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get the email properties, for better structure
     * @return the e
     */
    private static Properties getGmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }
    private static Session getEmailSession(){
        return Session.getInstance(getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }


    public static void main(String[] args)throws MessagingException {

        try{
            EmailSenderClass.sendEmail("s","Josue","Ticket");
        }catch (MessagingException e ){
            System.out.println(e.getMessage());
        }
    }
}
