package itmo.blps.lab1.config;

import jakarta.resource.ResourceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jca.support.ResourceAdapterFactoryBean;

@Configuration
public class JcaConfig {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${MAIL_USERNAME}")
    private String username;

    @Value("${MAIL_PASSWORD}")
    private String password;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${MAIL_BOX}")
    private String senderEmail;

    @Bean
    public ResourceAdapterFactoryBean resourceAdapter() {
        ResourceAdapterFactoryBean bean = new ResourceAdapterFactoryBean();
        bean.setResourceAdapter(new YandexResourceAdapter());
        return bean;
    }

    @Bean
    @Primary
    public YandexManagedConnectionFactory smtpManagedConnectionFactory() {
        YandexManagedConnectionFactory mcf = new YandexManagedConnectionFactory();
        mcf.setHost(host);
        mcf.setPort(port);
        mcf.setUsername(username);
        mcf.setPassword(password);
        mcf.setProtocol(protocol);
        mcf.setSenderEmail(senderEmail);
        return mcf;
    }

    @Bean
    public YandexConnectionFactory smtpConnectionFactory(
            YandexManagedConnectionFactory managedConnectionFactory) {
        return new YandexConnectionFactory(managedConnectionFactory, null);
    }

    @Bean
    public YandexConnection smtpConnection(YandexConnectionFactory connectionFactory)
            throws ResourceException {

        return connectionFactory.getConnection();
    }
}
