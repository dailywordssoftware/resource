package com.dailywords.resource.rest;


import com.dailywords.resource.domain.entity.Word;
import com.dailywords.resource.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/words")
@AllArgsConstructor
public class WordController {
    private final WordRepository wordRepository;

    @GetMapping
    public ResponseEntity<List<Word>> getAllWords() {
        return ResponseEntity.ok(wordRepository.findAll());
    }
}
