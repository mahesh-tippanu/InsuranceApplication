package com.example.insuranceuser.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("maheshkumartippanu@gmail.com");
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);
        mailSender.send(simpleMailMessage);
        System.out.println("Mail send to the user!!");
    }
    public void sendOtpMessage(String to, String subject, String message) throws MessagingException {

        SimpleMailMessage helper = new SimpleMailMessage();
        helper.setFrom("maheshkumartippanu@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message);
        System.out.println("Email not sent");
        mailSender.send(helper);
        System.out.println("Email sent");
    }
}
