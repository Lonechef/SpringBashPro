package com.advpro2.BatchProject.config;

import com.advpro2.BatchProject.entity.WalMartdata;
import com.advpro2.BatchProject.repository.WallMartrepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;



@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfig {

    @Autowired
    private WallMartrepo wallMartrepo;


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;


    // Create Reader
    @Bean
    @StepScope

    public FlatFileItemReader<WalMartdata> dataReader() {
        FlatFileItemReader<WalMartdata> itemReader = new FlatFileItemReader<>();
        try {
            itemReader.setResource(new FileSystemResource("src/main/resources/Walmart.csv"));

            itemReader.setName("csv-reader");
            itemReader.setLinesToSkip(1);
            itemReader.setLineMapper(lineMapper());
            System.out.println("CSV FILE FOUND");
            return itemReader;
        }
        catch(Error err){
            System.out.println("Unable to fetch CSV file");
            return  null;
        }

    }
    // Line Mapper useful to convert each line as a Java Object
    private LineMapper<WalMartdata> lineMapper() {
        DefaultLineMapper<WalMartdata> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setStrict(false);
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("id", "weeklysales", "temperature", "fuelprice", "unemployment");

        BeanWrapperFieldSetMapper<WalMartdata> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(WalMartdata.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    // Create Processor
    @Bean
    public WalMartdataConfig walMartdataConfig() {
        return new WalMartdataConfig();
    }

    // Create Writer
    @Bean
    public RepositoryItemWriter<WalMartdata> customWriter() {
        RepositoryItemWriter<WalMartdata> repositoryWriter = new RepositoryItemWriter<>();
        repositoryWriter.setRepository(wallMartrepo);
        repositoryWriter.setMethodName("save");
        return repositoryWriter;
    }

    // Create Step
    @Bean
    public Step step() {
        return new StepBuilder("step-1", jobRepository)
                .<WalMartdata, WalMartdata>chunk(10, transactionManager)
                .reader(dataReader())
                .processor(walMartdataConfig())
                .writer(customWriter())
                .build();
    }

    // Create Job
    @Bean
    public Job job() {
        return new JobBuilder("job-1", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }
}
