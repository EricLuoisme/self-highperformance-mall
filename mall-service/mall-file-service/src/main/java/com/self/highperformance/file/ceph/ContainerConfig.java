package com.self.highperformance.file.ceph;

import lombok.Data;
import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ceph")
@Data
public class ContainerConfig {

    private String username;
    private String password;
    private String authUrl;
    private String defaultContainerName;

    @Bean
    public Account account() {
        AccountConfig config = new AccountConfig();
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setAuthUrl(this.authUrl);
        config.setAuthenticationMethod(AuthenticationMethod.BASIC);
        return new AccountFactory(config).createAccount();
    }

    @Bean
    public Container container() {
        Container newContainer = account().getContainer(defaultContainerName);
        if (!newContainer.exists()) {
            return newContainer.create();
        }
        return newContainer;
    }


}
