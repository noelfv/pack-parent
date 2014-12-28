package com.everis.mail;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class Mail {

    private static Logger logger = Logger.getLogger(Mail.class);
    private Message message;
    private MimeMessage mime;

    public Mail() {
        this.message = null;
    }

    public Mail(Message message) {
        this.setMessage(message);
    }

    public void send() throws MailException {
        if (mime != null) {
            if (message == null) {
                throw new MailException(MailExceptionCodes.INVALID_PARAMETER, this.message);
            }

            try {
                mime.setContent(message.getBody().getMimeMultipart());
                Transport.send(mime);
            } catch (MessagingException e) {
                logger.error("Mensaje con error", e);
                throw new MailException(MailExceptionCodes.NOT_SEND, this.message);
            }
        } else {
            throw new MailException(MailExceptionCodes.INVALID_ADDRESS, this.message);
        }
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {

        logger.info("To: " + message.getHeader().getListTO());
        logger.info("CC: " + message.getHeader().getListCC());
        logger.info("CO: " + message.getHeader().getListCO());
        logger.info("Subject: " + message.getHeader().getSubject());
        logger.info("Host: " + message.getHeader().getHost());
        logger.info("Port: " + message.getHeader().getPort());

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", message.getHeader().getHost());
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", message.getHeader().getPort());
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.debug", "true");

        Session session = Session.getInstance(props, new MailAuthenticator(message));

        MimeMessage mime = new MimeMessage(session);

        try {
            if (message.getHeader().getEmailFrom() != null) {
                mime.setFrom(new InternetAddress(message.getHeader().getEmailFrom()));
            }

            if (message.getHeader().getListTO() != null && message.getHeader().getListTO().length() > 0) {
                mime.addRecipients(javax.mail.Message.RecipientType.TO, message.getHeader().getListTO());
            }

            if (message.getHeader().getListCC() != null && message.getHeader().getListCC().length() > 0) {
                mime.addRecipients(javax.mail.Message.RecipientType.CC, message.getHeader().getListCC());
            }

            if (message.getHeader().getListCO() != null && message.getHeader().getListCO().length() > 0) {
                mime.addRecipients(javax.mail.Message.RecipientType.BCC, message.getHeader().getListTO());
            }

            if (message.getHeader().getSubject() != null && message.getHeader().getSubject().length() > 0) {
                mime.setSubject(message.getHeader().getSubject(), "utf-8");
            }

        } catch (AddressException e) {
            logger.error("Direccion Invalida", e);
            mime = null;
        } catch (MessagingException e) {
            logger.error("Mensaje con error", e);
            mime = null;
        }

        this.message = message;
        this.mime = mime;
    }
}
