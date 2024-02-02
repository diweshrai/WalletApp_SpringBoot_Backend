package com.example.demo.ImplService;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.Config.QuartzConfig;

import com.example.demo.Service.QService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuartzSechedulingService implements QService {

	@Value("${mailSendingTime}")
	private long timeDuration;
	
	private final Scheduler scheduler;

	@Override
	public void scheduleEmailJob(String emailId) {
		JobDetail jobDetail = QuartzConfig.emailJobDetails(emailId);
		Trigger trigger = QuartzConfig.emailTrigger(jobDetail, timeDuration);
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
