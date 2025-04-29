package itmo.blps.lab1.config;

import jakarta.resource.ResourceException;
import jakarta.resource.spi.ConnectionManager;

public class YandexConnectionFactory {
    private final YandexManagedConnectionFactory mcf;
    private final ConnectionManager cm;

    public YandexConnectionFactory(YandexManagedConnectionFactory mcf, ConnectionManager cm) {
        this.mcf = mcf;
        this.cm = cm;
    }

    public YandexConnection getConnection() throws ResourceException {
        if (cm != null) {
            return (YandexConnection) cm.allocateConnection(mcf, null);
        } else {
            YandexManagedConnection managedConnection = (YandexManagedConnection) mcf.createManagedConnection(null, null);
            return (YandexConnection) managedConnection.getConnection(null, null);
        }
    }

    public YandexConnection getConnection(String username, String password) throws ResourceException {
        YandexConnectionRequestInfo cri = new YandexConnectionRequestInfo(username, password);
        if (cm != null) {
            return (YandexConnection) cm.allocateConnection(mcf, cri);
        } else {
            YandexManagedConnection managedConnection = (YandexManagedConnection) mcf.createManagedConnection(null, cri);
            return (YandexConnection) managedConnection.getConnection(null, cri);
        }
    }
}
