package com.dailywords.resource.domain;

import lombok.Data;

@Data
public class Result {
    private String definition;
    private String partOfSpeech;
    private String[] synonyms;
    private String[] typeOf;
    private String[] memberOf;
}

