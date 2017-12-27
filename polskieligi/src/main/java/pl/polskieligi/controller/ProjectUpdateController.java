package pl.polskieligi.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectUpdateController {
	String message = "Welcome to Spring MVC!";

	@Autowired
	JobExplorer jobExplorer;

	@RequestMapping("/updateProject")
	public ModelAndView showUpdateInfo() {
		List<ProjectUpdateJobRow> rows = new ArrayList<ProjectUpdateJobRow>();
		for (String jobName : jobExplorer.getJobNames()) {

			List<JobInstance> list = jobExplorer.findJobInstancesByJobName(jobName, 0, 20);
			for (JobInstance jobInstance : list) {
				List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
				for (JobExecution jobExecution : jobExecutions) {
 
					ProjectUpdateJobRow row = new ProjectUpdateJobRow();
					row.setJobExecution(jobExecution);
					row.setProgress(getProgress(jobExecution));
					Date endTime = jobExecution.getEndTime();
					if(endTime!=null) {
						row.setProcessingTime(endTime.getTime()-jobExecution.getStartTime().getTime());
					}
					rows.add(row);
				}
			}
		}
		Collections.sort(rows,(ProjectUpdateJobRow o1, ProjectUpdateJobRow o2) -> o1.getJobExecution().getId().intValue()-o2.getJobExecution().getId().intValue());
		
		ModelAndView mv = new ModelAndView("updateProject", "rows", rows);
		return mv;
	}
	
	private Long getProgress(JobExecution jobExecution) {
		Long result = Long.valueOf(0);
		if (jobExecution != null) {
			ExecutionContext ec = jobExecution.getExecutionContext();
			if (ec != null &&  ec.containsKey("jobComplete")) {
				double jobComplete = (Double) ec.get("jobComplete");
				double reads = 0;
				for (StepExecution step : jobExecution.getStepExecutions()) {						
					reads = reads + step.getReadCount();
				}							
				result = Math.round(reads / jobComplete * 100);
			} 
		} 
		return result;
	}

	public class ProjectUpdateJobRow {
		JobExecution jobExecution;
		Long progress;
		Long processingTime;
		public JobExecution getJobExecution() {
			return jobExecution;
		}
		public void setJobExecution(JobExecution jobExecution) {
			this.jobExecution = jobExecution;
		}
		public Long getProgress() {
			return progress;
		}
		public void setProgress(Long progress) {
			this.progress = progress;
		}
		public Long getProcessingTime() {
			return processingTime;
		}
		public void setProcessingTime(Long processingTime) {
			this.processingTime = processingTime;
		}
	}
}
