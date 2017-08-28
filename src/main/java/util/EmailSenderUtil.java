package util;

import dto.Operation;
import dto.Transaction;
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

	private final Logger LOG = LoggerFactory.getLogger(EmailSenderUtil.class);

	public void sendEmail(User user, Object source, Operation operation) {
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

		String text = null;
		String emailSubject = null;

		if (operation.equals(Operation.REGISTER)) {
			String regURL = String.format("<a href=http://redgyuf.servebeer.com:4200/verify/%s>Verify my account</a>",
					source);
			emailSubject = "Verification e-mail for CoolDrive";
			text = String.format(
					"Dear %s,<br>Thank you for registration for <b>CoolDrive!</b><br>"
							+ "Your account informations:<br>" + "<hr>Username: %s<br>Starting quantity: 50 MB<br>"
							+ "<hr><br>For start to use our services please verify your account with this link: %s"
							+ "<br><br><br><i>Codecool CoolDrive Â©</i>",
					user.getFirstName() + " " + user.getLastName(), user.getUserName(), regURL);
			LOG.info("Verification email process initilizing!");
			
		} else if (operation.equals(Operation.NEWTRANSACTION)) {
			Transaction transaction = (Transaction)source;
			emailSubject = "Transaction verification email for CoolDrive";
			text = String.format(
					"Dear %s,<br>Thank you for purchasing additional storage!<br>"
							+ "Your transaction details:<br>" 
							+ "<hr>Username: %s<br>"
							+ "<hr>Transaction ID: %s<br>"
							+ "<hr>Zip code: %s<br>"
							+ "<hr>City: %s<br>"
							+ "<hr>Address %s<br>"
							+ "<hr>Billing address: %s<br>"
							+ "<hr>Phone number: %s<br>"
							+ "<hr>Package: %s MB<br>"
							+ "<br><br><br><i>Codecool CoolDrive ©</i>",
					user.getFirstName() + " " + user.getLastName(), user.getUserName(), transaction.getId(), transaction.getZip(), transaction.getCity(), transaction.getAddress1(), transaction.getAddress2(), transaction.getPhone(), transaction.getBought());
			LOG.info("Transaction verification email process initializing!");
		}

		try {
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			// mimeBodyPart.setContent(text, "text/html");
			mimeBodyPart.setText(text, "UTF-8", "html");
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setContent(multipart);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject(emailSubject);
			// message.setText(text);
			Transport.send(message);
			LOG.info("Email send to {}", user.getEmail());
		} catch (MessagingException e) {
			LOG.error("sendEmail method is failed with exception", e);
			throw new RuntimeException(e);
		}
	}
}
