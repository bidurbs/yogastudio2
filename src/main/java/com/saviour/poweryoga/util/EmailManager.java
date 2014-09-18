/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.util;

import javax.inject.Singleton;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 *
 * @author Md Mojahidul Islam
 * @version 0.0.1
 */
@Component
@Singleton
public class EmailManager {

    /**
     * This method will send email to customer/faculty/admin etc
     *
     * @param mailSender Java email sender
     * @param subject Email subject
     * @param body Email body
     * @param mailTo Mail to information
     */
    public static void sendEmail(JavaMailSender mailSender, String subject, String body, String mailTo) {

        SimpleMailMessage email = new SimpleMailMessage();
        //String link = "http://localhost:8080/mycompany.com/activation/" + encodedUser;
        email.setText(body);
        email.setTo(mailTo);
        email.setSubject(subject);
        mailSender.send(email);
    }

}
