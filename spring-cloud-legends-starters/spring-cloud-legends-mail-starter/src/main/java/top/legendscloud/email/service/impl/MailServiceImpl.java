package top.legendscloud.email.service.impl;

import top.legendscloud.email.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * 发送邮件实现类
 * @Author herion
 * @Description //TODO
 * @Date 21:25 2019/8/29
 * @Param
 * @return
 **/
@Service
public class MailServiceImpl implements IMailService {

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    private JavaMailSender javaMailSender;

    public void setFrom(String from) {
        this.from = from;
    }

    @Value("${spring.mail.from}")
    private String from;

    /**
     * 发送文本邮件
     *
     * @param to
     *          邮件接收方
     * @param subject
     *          邮件标题
     * @param content
     *          邮件内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    /**
     *
     * @param to
     *          邮件接收方
     * @param subject
     *          邮件标题
     * @param content
     *          邮件内容
     * @param cc
     *          抄送方
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }



    /**
     * 发送 HTML 邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        // true 表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }


    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        FileSystemResource file = new FileSystemResource(new File(filePath));
        // 截取附件名
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        helper.addAttachment(fileName, file);

        javaMailSender.send(message);
    }
    /**
     * 发送正文中有静态资源（图片）的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);

        javaMailSender.send(message);
    }


}