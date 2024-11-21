package lk.ijse.gdse.bbms.util;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    public static boolean sendEmail(String recipientEmail, String subject, String messageBody) {
        // Sender's email credentials
        final String senderEmail = "ruwaniranthika2001@gmail.com";
        final String senderPassword = "nzdb wpjr ymhc jnqx";

        // Configuring mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a new session with the authenticated credentials
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}