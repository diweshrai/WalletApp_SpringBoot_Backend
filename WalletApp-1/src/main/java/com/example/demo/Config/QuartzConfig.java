package com.example.demo.Config;

import java.util.UUID;

import org.jfree.util.Log;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.mail.javamail.JavaMailSender;

import com.example.demo.ImplService.EmailSendingJob;


import com.example.demo.Service.CustomerService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

	
	private final JavaMailSender javaMailSender;
	
	private final CustomerService customerService;


	@Bean
	public EmailSendingJob emailJob() {
		return new EmailSendingJob(javaMailSender, customerService);
	}

	public static JobDetail emailJobDetails(String emailId) {
		Log.info("job fire " + emailId);
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("emailId", emailId);
		return JobBuilder.newJob(EmailSendingJob.class).withIdentity(UUID.randomUUID().toString(), "email-jobs")
				.withDescription("email Send").usingJobData(jobDataMap).storeDurably().build();
	}

	public static Trigger emailTrigger(JobDetail job, long timeDuration) {
		return TriggerBuilder.newTrigger().forJob(job).withIdentity(job.getKey().getName(), "email Triger")
				.withDescription("send Email trigger")
				.startAt(DateBuilder.futureDate((int) timeDuration, DateBuilder.IntervalUnit.MINUTE))
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow()).build();
	}
}