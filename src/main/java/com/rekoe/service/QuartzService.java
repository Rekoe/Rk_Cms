package com.rekoe.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

@IocBean(create = "init", depose = "depose")
public class QuartzService {

	private Scheduler scheduler;

	public void addTask(Class<Job> cls, String cron) throws SchedulerException {
		String clsName = cls.getName();
		JobDetail job = newJob(cls).withIdentity(clsName + "Job", clsName + "Group").build();
		CronTrigger trigger = newTrigger().withIdentity(clsName + "Trigger", clsName + "Group").withSchedule(cronSchedule(cron)).build();
		this.scheduler.scheduleJob(job, trigger);
	}

	public void init() throws SchedulerException {
		this.scheduler = StdSchedulerFactory.getDefaultScheduler();
		this.scheduler.start();
	}

	public void depose() throws SchedulerException {
		scheduler.shutdown(true);
	}

	public JobDetail find(String name, String group) throws SchedulerException {
		JobKey jobKey = new JobKey(name, group);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		return jobDetail;
	}

	public boolean delete(String name, String group) throws SchedulerException {
		return scheduler.deleteJob(new JobKey(name, group));
	}
}
