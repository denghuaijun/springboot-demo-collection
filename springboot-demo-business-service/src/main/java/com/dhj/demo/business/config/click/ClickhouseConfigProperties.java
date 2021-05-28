package com.dhj.demo.business.config.click;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Component
@ConfigurationProperties(prefix = "clickhouse.datasource")
public class ClickhouseConfigProperties {

    private String url;

    private String driver;

    private String user;

    private String password;

    private Boolean useLoadbalancer = true;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getUseLoadbalancer() {
        return useLoadbalancer;
    }

    public void setUseLoadbalancer(Boolean useLoadbalancer) {
        this.useLoadbalancer = useLoadbalancer;
    }
}
