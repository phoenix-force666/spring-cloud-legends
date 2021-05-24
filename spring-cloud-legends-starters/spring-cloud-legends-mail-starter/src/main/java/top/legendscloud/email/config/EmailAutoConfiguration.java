package top.legendscloud.email.config;

import top.legendscloud.email.service.IMailService;
import top.legendscloud.email.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.Properties;

/**
 * @author herion
 * @version V1.0
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date ${date}
 */
@Configuration
@EnableConfigurationProperties({MyMailProperties.class})
public class EmailAutoConfiguration{
    @Autowired
    private MyMailProperties myMailProperties;

    @Value("${spring.mail.from}")
    private String from;


    @Bean
    public JavaMailSender javaMailSender() throws MessagingException {
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        Properties properties=new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.transport.protocol",myMailProperties.getProtocol());
        properties.put("mail.smtp.port", myMailProperties.getPort());
        properties.putAll(myMailProperties.getProperties());

        javaMailSender.setHost(myMailProperties.getHost());
        javaMailSender.setUsername(myMailProperties.getUsername());
        javaMailSender.setPassword(myMailProperties.getPassword());
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setDefaultEncoding(myMailProperties.getDefaultEncoding().toString());

       return javaMailSender;
    }

    @Bean
    @ConditionalOnMissingBean(IMailService.class)
    public IMailService mailService(){
        MailServiceImpl mailService=new MailServiceImpl();
        mailService.setFrom(from);
        return new MailServiceImpl();
    }

}