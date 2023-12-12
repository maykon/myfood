package com.myfood.services;

import com.myfood.exceptions.EmailNotSentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class EmailService {
    @Value("${api.email.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(from);
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            message.setContent(body, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (Exception ex) {
            throw new EmailNotSentException("Email not sent", ex);
        }
    }

    private String getEmailTemplate(String name) throws IOException {
        var resource = ResourceUtils.getFile("classpath:".concat(name));
        return new String(Files.readAllBytes(resource.toPath()));
    }

    public void sendNewUserEmail(String to) throws MessagingException, IOException {
        var template = getEmailTemplate("email/new_user.html");
        template = template.replace("${email}", to);
        this.sendEmail(to, "New User Registered", template);
    }

    public void sendPasswordChangedEmail(String to) throws MessagingException, IOException {
        var template = getEmailTemplate("email/password.html");
        template = template.replace("${email}", to);
        this.sendEmail(to, "Password changed", template);
    }
}
