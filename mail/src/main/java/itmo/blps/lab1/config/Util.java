package itmo.blps.lab1.config;

import jakarta.resource.ResourceException;
import jakarta.resource.spi.ConnectionRequestInfo;
import jakarta.resource.spi.ManagedConnectionFactory;
import jakarta.resource.spi.security.PasswordCredential;

import javax.security.auth.Subject;
import java.util.Set;

public class Util {
    public static PasswordCredential getPasswordCredential(
            final ManagedConnectionFactory mcf,
            final Subject subject,
            final ConnectionRequestInfo cri) throws ResourceException {

        if (subject == null) {
            return null;
        }

        Set<PasswordCredential> credentials = subject.getPrivateCredentials(PasswordCredential.class);

        for (PasswordCredential pc : credentials) {
            if (pc.getManagedConnectionFactory().equals(mcf)) {
                return pc;
            }
        }

        return null;
    }
}
