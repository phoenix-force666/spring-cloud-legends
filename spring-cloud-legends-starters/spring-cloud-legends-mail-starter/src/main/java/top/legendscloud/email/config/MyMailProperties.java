package top.legendscloud.email.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author herion
 * @version V1.0
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date ${date}
 */
@ConfigurationProperties(prefix = "spring.mail")
public class MyMailProperties{
    private static final Charset DEFAULT_CHARSET;
    private String host="smtp.exmail.qq.com";
    private Integer port=25;
    private String username;
    private String password;
    private String protocol = "smtp";
    private Charset defaultEncoding=Charset.forName("UTF-8");
    private Map<String, String> properties;
    private String jndiName;
    private boolean testConnection;

    public MyMailProperties() {
        this.defaultEncoding = DEFAULT_CHARSET;
        this.properties = new HashMap();
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Charset getDefaultEncoding() {
        return this.defaultEncoding;
    }

    public void setDefaultEncoding(Charset defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public String getJndiName() {
        return this.jndiName;
    }

    public boolean isTestConnection() {
        return this.testConnection;
    }

    public void setTestConnection(boolean testConnection) {
        this.testConnection = testConnection;
    }

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
    }


}