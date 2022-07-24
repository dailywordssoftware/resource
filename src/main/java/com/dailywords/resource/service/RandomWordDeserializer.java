package com.dailywords.resource.service;

import com.dailywords.resource.domain.RandomWord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class RandomWordDeserializer implements Deserializer<RandomWord> {
    @Override
    public RandomWord deserialize(String s, byte[] bytes) {
        try {
            return new ObjectMapper().readValue(bytes, RandomWord.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
