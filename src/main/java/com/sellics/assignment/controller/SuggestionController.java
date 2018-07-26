package com.sellics.assignment.controller;


import com.sellics.assignment.dto.Suggestion;
import com.sellics.assignment.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SuggestionController {
    @Autowired
    SuggestionService suggestionService;


    @GetMapping("suggestion")
    public List<Suggestion> suggestions(@RequestParam String keyword){
        return suggestionService.getAmazonSuggestions(keyword);
    }
}
