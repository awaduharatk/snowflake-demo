package snowflake.batch.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import snowflake.batch.demo.step.tasklet.DemoTasklet;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final DemoTasklet demoTasklet;

    public BatchConfig(JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        DemoTasklet demoTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.demoTasklet = demoTasklet;
    }

    @Bean
    public Job demoJob(Step demoStep) {
        return jobBuilderFactory.get("demoJob")
            .flow(demoStep)
            .end()
            .build();
    }

    @Bean
    public Step demoStep() {
        return stepBuilderFactory.get("demoStep")
            .tasklet(demoTasklet)
            .build();
    }
}
