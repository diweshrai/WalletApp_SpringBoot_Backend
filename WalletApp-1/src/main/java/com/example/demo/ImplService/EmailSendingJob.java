package com.example.demo.ImplService;

import java.util.Optional;


import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import com.example.demo.Model.Customer;
import com.example.demo.Service.CustomerService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailSendingJob  implements Job{
	
	private final JavaMailSender javaMailSender;

	private final CustomerService customerService;
	
	@Override
	public void execute(JobExecutionContext context)  {
		
		try {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		   String customerEmail = jobDataMap.getString("emailId");
		   log.info("Inside Send "+customerEmail);
		   Optional<Customer> customer = customerService.findByEmail(customerEmail);
		    sendEmail(customer);
		} catch (Exception e) {
		}
	}

private void sendEmail(Optional<Customer> customer) throws MessagingException{
	String customerFullName = (customer.get().getFirstName()+" "+customer.get().getLastName());
	MimeMessage message  = javaMailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(message , false);
	log.info("trigger fire to "+ customer.get().getEmailId());
	helper.setTo(customer.get().getEmailId().toLowerCase());
	helper.setSubject("Welcome back"+ " "+customerFullName);
	helper.setText("Dear "+customerFullName+ ",\n\n"
          + "Welcome to Wallet Application! We are thrilled to have you on board.\n\n"
          + "Thank you for choosing our services. Feel free to explore and enjoy the features of our Wallet Application.\n\n"
          + "If you have any questions or need assistance, don't hesitate to reach out to our support team.\n\n"
          + "Best regards,\nThe Wallet Application Team");
	javaMailSender.send(message);
}

        }