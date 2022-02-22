/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;






/**
 *
 * @author ibeno
 */
public class SendMail {
   
public static void send(String recepient,String subject,String object) throws Exception {
      
            System.out.println("preparing to send email");
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            
            String myAccountEmail = "freeetudiant120@gmail.com";
            String password = "123aA456*";
            
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(myAccountEmail, password);
                }
            });
            
            javax.mail.Message message = prepareMessage(session,myAccountEmail,recepient,subject,object);
            
            Transport.send(message);
            
            
            
            System.out.println("meesage sent successfully");
        

}
    
      private static javax.mail.Message prepareMessage(Session session, String myAccountEmail, String sendTo,String subject,String object) {
      javax.mail.Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(sendTo));
            message.setSubject(subject);
            message.setText(object);
            return message;
        } catch (Exception ex) {
           // Logger.getLogger(AddEventForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;




}

}
