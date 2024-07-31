package com.advpro2.BatchProject.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsDataController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @GetMapping("/walmartsales")
    public void loadCsvToDb() throws Exception {
        JobParameters jobParams = new JobParametersBuilder()
                .addLong("Start-At", System.currentTimeMillis())
                .toJobParameters();
        // Restcontroller will launch the job; in job we have step and in step all read-write processes are there
        jobLauncher.run(job, jobParams);
    }
}
