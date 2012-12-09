/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.ejb;

import edu.fullerton.AcademyAdvisorAppointment.entity.Admin;
import edu.fullerton.AcademyAdvisorAppointment.entity.Person;
import java.io.UnsupportedEncodingException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author wujun
 */
@Stateless
public class MailBean {
    @EJB
    ConfigBean configbBean;
    public void sendMail(Person person, String subject, String content) throws NamingException, MessagingException, UnsupportedEncodingException {
        InitialContext ctx = new InitialContext();  
        Session session =  
            (Session) ctx.lookup("mail/admin");  
        // Or by injection.  
        //@Resource(name = "mail/<name>")  
        //private Session session;  
  
        // Create email and headers.  
        Message msg = new MimeMessage(session);
        msg.setSubject(subject);  
        msg.setRecipient(RecipientType.TO,
                         new InternetAddress(  
                         person.getEmail(),  
                         person.getName()));
//        Admin admin=configbBean.getAdmin();
        /*msg.setFrom(new InternetAddress(  
                    admin.getEmail(),  
                    "Jack"));*/
  
        msg.setText(content);  
  
        // Send email.  
        Transport.send(msg);  
    }
}
