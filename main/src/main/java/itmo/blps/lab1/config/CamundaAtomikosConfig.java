package itmo.blps.lab1.config;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.spring.AtomikosDataSourceBean;
import jakarta.transaction.UserTransaction;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class CamundaAtomikosConfig {

    @Value("${spring.datasource.xa.server-name}")
    private String serverName;

    @Value("${spring.datasource.xa.database-name}")
    private String databaseName;

    @Value("${spring.datasource.xa.port}")
    private String port;

    @Value("${spring.datasource.xa.user}")
    private String user;

    @Value("${spring.datasource.xa.password}")
    private String password;

    // Общий TransactionManager (используется и для JPA и для Camunda)
    @Bean
    public PlatformTransactionManager jtaTransactionManager(
            UserTransaction userTransaction,
            UserTransactionManager atomikosTransactionManager) {
        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    }

    // DataSource для Camunda (использует ту же БД, но отдельный пул соединений)
    @Bean(name = "camundaDataSource")
    @DependsOn("atomikosTransactionManager")
    public DataSource camundaDataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setUniqueResourceName("camundaXa");
        ds.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
        ds.setXaProperties(camundaXaProperties());
        ds.setPoolSize(5);
        return ds;
    }


    private Properties camundaXaProperties() {
        Properties props = new Properties();
        props.put("user", user);
        props.put("password", password);
        props.put("serverName", serverName);
        props.put("portNumber", port);
        props.put("databaseName", databaseName);
        return props;
    }

    // Конфигурация движка Camunda
    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration(
            PlatformTransactionManager jtaTransactionManager,
            @Qualifier("camundaDataSource") DataSource camundaDataSource) throws IOException {

        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

        // Настройки БД
        config.setDataSource(camundaDataSource);
        config.setTransactionManager(jtaTransactionManager);
        config.setDatabaseSchemaUpdate("true");
        config.setHistory("full");
        config.setJobExecutorActivate(true);
        config.setAuthorizationEnabled(true);


        // Настройки JTA
        config.setTransactionsExternallyManaged(true);

        // Настройка автодеплоя
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] bpmnResources =
                resolver.getResources("classpath:/processes/**/*.bpmn");
        Resource[] formResources =
                resolver.getResources("classpath:/forms/**/*.form");
        Resource[] allResources = new Resource[bpmnResources.length + formResources.length];
        System.arraycopy(bpmnResources, 0, allResources, 0, bpmnResources.length);
        System.arraycopy(formResources, 0, allResources, bpmnResources.length, formResources.length);

        config.setDeploymentResources(allResources);
        config.setDeploymentName("AutoDeployment_" + System.currentTimeMillis());

        return config;
    }

    // Фабрика ProcessEngine
    @Bean
    public ProcessEngineFactoryBean processEngine(
            SpringProcessEngineConfiguration processEngineConfiguration) {
        ProcessEngineFactoryBean factory = new ProcessEngineFactoryBean();
        factory.setProcessEngineConfiguration(processEngineConfiguration);
        return factory;
    }

}
