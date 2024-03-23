package com.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class HelloJobConfiguration {

    @Bean
    public Job helloJob(JobRepository jobRepository, Step helloStep1, Step helloStep2) {
        return new JobBuilder("helloJob", jobRepository)
                .start(helloStep1)
                .next(helloStep2)
                .build();
    }

    @Bean
    public Step helloStep1(JobRepository jobRepository, Tasklet tasklet1, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloStep1", jobRepository)
                .tasklet(tasklet1, platformTransactionManager)
                .build();
    }

    @Bean
    public Step helloStep2(JobRepository jobRepository, Tasklet tasklet2, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloStep2", jobRepository)
                .tasklet(tasklet2, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet tasklet1() {
        return ((contribution, chunkContext) -> {
            log.info("=======================");
            log.info(" >> Hello Spring Batch ");
            log.info("=======================");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Tasklet tasklet2() {
        return ((contribution, chunkContext) -> {
            log.info("=======================");
            log.info(" >> Step2 has executed ");
            log.info("=======================");
            return RepeatStatus.FINISHED;
        });
    }

}
