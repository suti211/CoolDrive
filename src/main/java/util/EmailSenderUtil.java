package util;

import dto.User;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by David Szilagyi on 2017. 08. 01..
 */
public class EmailSenderUtil {

    public static void sendEmail(User user, String token) {
        final String username = "cooldrive.codecool@gmail.com";
        final String password = "CoolDrive2017.";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject("Verification e-mail for CoolDrive");
            String regURL = String.format("http://localhost:8080/CoolDrive/user/verify?email=%s&token=%s", user.getEmail(), token);
            String text = String.format("Dear %s,\nThank you for registration for CoolDrive!\n" +
                    "Your account informations:\n" +
                    "------------------------------\nUsername: %s\nStarting quantity: 50 MB\n" +
                    "------------------------------\nFor start use our services please verify your account with this link:\n" +
                    "%s\n\n\nCodecool CoolDrive ©", user.getFirstName() + " " + user.getLastName(), user.getUserName(), regURL);
            message.setText(text);
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
