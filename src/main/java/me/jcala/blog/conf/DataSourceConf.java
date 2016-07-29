package me.jcala.blog.conf;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
/**
 * Created by jcala on 2016/7/29
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConf {
    @Value("${hikaricp.driver}")
    private String driver;
    @Value("${hikaricp.url}")
    private String url;
    @Value("${hikaricp.username}")
    private String username;
    @Value("${hikaricp.password}")
    private String password;
    @Value("${hikaricp.connectionTimeout}")
    private long connectionTimeout;
    @Value("${hikaricp.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${hikaricp.minimumIdle}")
    private int minimumIdle;

    @Bean(destroyMethod = "close")
    public DataSource hikariDataSource() {
        HikariDataSource hds = new HikariDataSource();
        hds.setUsername(username);
        hds.setPassword(password);
        hds.setJdbcUrl(url);
        hds.setDriverClassName(driver);
        hds.setConnectionTimeout(connectionTimeout);
        hds.setMaximumPoolSize(maximumPoolSize);
        hds.setMinimumIdle(minimumIdle);
        return hds;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager =
                new DataSourceTransactionManager(hikariDataSource());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
}