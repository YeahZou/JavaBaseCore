package com.yeah.javabasic.util.job;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TestJob {

	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		
		registerJobAndTrigger(scheduler);
	}
	
	public static void registerJobAndTrigger(Scheduler scheduler) {
		JobDetail job = JobBuilder.newJob(MyJob.class)
				.withIdentity("myJob", "myGroup")
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("myTrigger", "myTriggerGroup")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
				.build();
		
		try {
			scheduler.scheduleJob(job, trigger);
		} catch(Exception e) {
			System.out.println("注册任务和触发器失败");
		}
	}
	
	public static class MyJob implements Job {
		@Override
		public void execute(JobExecutionContext context) {
			System.out.println("执行作业成功");
		}
	}
}

