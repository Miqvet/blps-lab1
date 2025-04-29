package itmo.blps.lab1.config;

import jakarta.resource.spi.ConnectionRequestInfo;

import java.util.Objects;

public class YandexConnectionRequestInfo implements ConnectionRequestInfo {
    private final String username;
    private final String password;

    public YandexConnectionRequestInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YandexConnectionRequestInfo that = (YandexConnectionRequestInfo) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
