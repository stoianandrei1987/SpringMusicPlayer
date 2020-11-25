package ro.andreistoian.SpringMusicPlayer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Value("${andrei.no-docker.datasource.url}")
    String noDocker;
    @Value("${andrei.docker.datasource.url}")
    String docker;
    @Value("${andrei.run-on-docker}")
    boolean runOnDocker;
    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;
    @Value("${spring.datasource.username}")
    String userName;
    @Value("${spring.datasource.password}")
    String passWord;


    @Bean
    public DataSource getDataSource(){



        DataSourceBuilder builder = DataSourceBuilder.create();
        if(runOnDocker) builder.url(docker);
        else builder.url(noDocker);
        return builder.
                driverClassName(driverClassName).
                username(userName).
                password(passWord).
                build();

    }

}
