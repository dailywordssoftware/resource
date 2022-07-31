package com.dailywords.resource.batch;

import com.dailywords.resource.domain.RandomWord;
import com.dailywords.resource.service.RandomWordDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

@EnableBatchProcessing
@Configuration
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Value("${spring.kafka.template.default-topic}")
    private String randomWordsTopic;
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")
    private String randomWordsGroupId;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    private Properties kafkaConsumerProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, RandomWordDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, randomWordsGroupId);
        return props;
    }

    @Bean
    public KafkaItemReader<String, RandomWord> kafkaItemReader() {
        return new KafkaItemReaderBuilder<String, RandomWord>()
                .partitions(0)
                .consumerProperties(kafkaConsumerProperties())
                .name("kafkaItemReader")
                .topic(randomWordsTopic)
                .build();
    }

//    @Bean
//    public ItemWriter<RandomWord> jdbcItemWriter() {
//        return new JdbcBatchItemWriterBuilder<RandomWord>()
//                .build();
//    }

    @Bean
    public Step fetchRandomWords() {
        return stepBuilderFactory.get("getRandomWordsStep")
                .<RandomWord, RandomWord>chunk(50)
                .reader(kafkaItemReader())
                .writer(new ItemWriter<RandomWord>() {
                    @Override
                    public void write(List<? extends RandomWord> list) throws Exception {
                        System.out.println(list);
                    }
                })
                .build();
    }

    @Bean
    public Job getRandomWordsJob() {
        return jobBuilderFactory.get("getRandomWords")
                .incrementer(new RunIdIncrementer())
                .start(fetchRandomWords())
                .build();
    }
}
