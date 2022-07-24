package com.dailywords.resource.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
public class RandomWord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String word;

    private List<Result> results;
}
