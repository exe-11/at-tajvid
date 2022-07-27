package uz.oliymahad.oliymahadquroncourse.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class EmailSenderConfig {


    private static final String HOST = "smtp.gmail.com";

    private static final int PORT = 587;

    protected static final String USERNAME = "bekmurotboyev@gmail.com";

    private static final String PASSWORD = "ybolqfoamnidlguo";



    @Bean
    public JavaMailSender getJavaMailSender(){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(HOST);
        mailSender.setPort(PORT);
        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);

        mailSender.setJavaMailProperties(getMailSenderProperties());

        return mailSender;
    }

    private Properties getMailSenderProperties(){
        Properties props = new Properties();
        props.put("spring.mail.transport.protocol", "smtp");
        props.put("spring.mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return props;
    }
}
