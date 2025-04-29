package itmo.blps.lab1.config;

import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.resource.ResourceException;
import jakarta.resource.spi.ConnectionEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YandexConnection {
    private final YandexManagedConnection managedConnection;
    private final Session mailSession;
    private final String from;
    private final String username;
    private boolean closed = false;

    public YandexConnection(YandexManagedConnection managedConnection, Session mailSession, String from, String username) {
        this.managedConnection = managedConnection;
        this.mailSession = mailSession;
        this.from = from;
        this.username = username;
    }

    public void send(String to, String from, String text) {
        try {
            checkIfClosed();

            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Order Details");
            message.setText(text); // Empty text as we'll add it to multipart

            // Create attachment part
//            MimeBodyPart attachmentPart = new MimeBodyPart();
//            attachmentPart.setContent(pdfContent, "application/pdf");
//            attachmentPart.setFileName("document.pdf");

//            MimeMultipart multipart = new MimeMultipart();
//            multipart.addBodyPart(attachmentPart);
//            message.setContent(multipart);

            // Send message
            String host = mailSession.getProperty("mail.smtp.host");
            int port = Integer.parseInt(mailSession.getProperty("mail.smtp.port"));
            try (Transport transport = mailSession.getTransport(mailSession.getProperty("mail.transport.protocol"))) {
                transport.connect(host, port, username, mailSession.getProperty("mail.smtp.password"));
                transport.sendMessage(message, message.getAllRecipients());
            }
        } catch (Exception e) {
            log.warn("Failed to send email: {}", e.getMessage());
        }
    }

    public void close() throws ResourceException {
        if (!closed) {
            closed = true;
            managedConnection.fireConnectionEvent(ConnectionEvent.CONNECTION_CLOSED, this, null);
        }
    }

    void invalidate() {
        closed = true;
    }

    private void checkIfClosed() throws ResourceException {
        if (closed) {
            throw new ResourceException("Connection is closed");
        }
    }
}
