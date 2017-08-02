package util;

import dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by David Szilagyi on 2017. 08. 01..
 */
public class EmailSenderUtil {

    private static final Logger LOG = LoggerFactory.getLogger(EmailSenderUtil.class);

    public static void sendEmail(User user, String token) {
        LOG.info("sendEmail method is called from: {}", user.getEmail());
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

        String regURL = String.format("<a href=http://localhost:8080/CoolDrive/verify?token=%s>Verify my account</a>", user.getEmail(), token);
        String text = String.format("Dear %s,<br>Thank you for registration for <b>CoolDrive!</b><br>" +
                "Your account informations:<br>" +
                "<hr>Username: %s<br>Starting quantity: 50 MB<br>" +
                "<hr><br>For start to use our services please verify your account with this link: %s" +
                "<br><br><br><i>Codecool CoolDrive ©</i>", user.getFirstName() + " " + user.getLastName(), user.getUserName(), regURL);

        try {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setContent(text, "text/html");
            mimeBodyPart.setText(text, "UTF-8", "html");
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setContent(multipart);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject("Verification e-mail for CoolDrive");
//            message.setText(text);
            Transport.send(message);
            LOG.info("Email send to {} and waiting for validation", user.getEmail());
        } catch (MessagingException e) {
            LOG.error("sendEmail method is failed with exception", e);
            throw new RuntimeException(e);
        }
    }
}
