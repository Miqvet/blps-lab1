package itmo.blps.lab1.config;

import jakarta.resource.ResourceException;
import jakarta.resource.spi.*;
import jakarta.resource.spi.security.PasswordCredential;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.security.auth.Subject;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = {"host", "port", "username", "protocol"})
@ConnectionDefinition(connectionFactory = YandexConnectionFactory.class, connectionFactoryImpl = YandexConnectionFactory.class, connection = YandexConnection.class, connectionImpl = YandexConnection.class)
public class YandexManagedConnectionFactory implements ManagedConnectionFactory, ResourceAdapterAssociation {
    private static final long serialVersionUID = 1L;

    private String host;
    private int port;
    private String username;
    private String password;
    private String protocol;
    private String senderEmail;
    private PrintWriter logWriter;
    private YandexResourceAdapter resourceAdapter;

    @Override
    public Object createConnectionFactory(ConnectionManager cm) throws ResourceException {
        return new YandexConnectionFactory(this, cm);
    }

    @Override
    public Object createConnectionFactory() throws ResourceException {
        return new YandexConnectionFactory(this, null);
    }

    @Override
    public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cri)
            throws ResourceException {

        String userName = this.username;
        String password = this.password;

        if (subject != null) {
            PasswordCredential pc = Util.getPasswordCredential(this, subject, cri);
            if (pc != null) {
                userName = pc.getUserName();
                password = new String(pc.getPassword());
            }
        }

        if (cri != null && cri instanceof YandexConnectionRequestInfo) {
            YandexConnectionRequestInfo smtpCri = (YandexConnectionRequestInfo) cri;
            if (smtpCri.getUsername() != null) {
                userName = smtpCri.getUsername();
            }
            if (smtpCri.getPassword() != null) {
                password = smtpCri.getPassword();
            }
        }

        Properties mailProps = new Properties();
        mailProps.put("mail.transport.protocol", protocol);

        mailProps.put("mail.smtp.host", host);
        mailProps.put("mail.smtp.port", String.valueOf(port));
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.ssl.enable", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailProps.put("mail.smtp.user", userName);
        mailProps.put("mail.smtp.password", password);
        mailProps.put("mail.debug", "false");

        return new YandexManagedConnection(this, userName, password, mailProps);
    }

    @Override
    public ManagedConnection matchManagedConnections(@SuppressWarnings("rawtypes") Set connections, Subject subject,
                                                     ConnectionRequestInfo cri) throws ResourceException {

        PasswordCredential pc = null;
        if (subject != null) {
            pc = Util.getPasswordCredential(this, subject, cri);
        }

        for (Object connection : connections) {
            if (connection instanceof YandexManagedConnection managedConn) {

                if (managedConn.getFactory().equals(this)) {
                    if (pc == null || managedConn.passwordMatches(pc.getUserName(), new String(pc.getPassword()))) {
                        return managedConn;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.logWriter = out;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return this.logWriter;
    }

    @Override
    public ResourceAdapter getResourceAdapter() {
        return this.resourceAdapter;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
        if (ra != null && !(ra instanceof YandexResourceAdapter)) {
            throw new ResourceException("Invalid resource adapter provided");
        }
        this.resourceAdapter = (YandexResourceAdapter) ra;
    }
}
