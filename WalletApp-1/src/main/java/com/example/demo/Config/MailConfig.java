//package com.example.demo.Config;
//
//
//
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//@Configuration
//public class MailConfig {
//
//	@Value("${spring.mail.host}")
//	private String mailServerHost;
//	
//	@Value("${spring.mail.port}")
//	private int portNumber;
//	
//	@Value("${spring.mail.username}")
//	private String userName;
//	
//	@Value("${spring.mail.password}")
//	private String password;
//	
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        // Set mailSender properties from application.properties
//        mailSender.setHost(mailServerHost);
//        mailSender.setPort(portNumber);
//        mailSender.setUsername(userName);
//        mailSender.setPassword(password);
//        
//        
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.starttls.enable", "true");
//
//        mailSender.setJavaMailProperties(properties);
//
//        return mailSender;
//    }
//}
