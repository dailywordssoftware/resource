package com.dailywords.resource.service;

import com.dailywords.resource.domain.RandomWord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    @KafkaListener(topics = "randomDailyWords", groupId = "dailyWords")
    public void listener(RandomWord randomWord) {
        System.out.println("DATA: " + randomWord);
    }
}
