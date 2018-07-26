package com.sellics.assignment.service;

import com.sellics.assignment.dto.Suggestion;
import com.sellics.assignment.dto.SuggestionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SuggestionService {


    /**
     * Gets a suggestions from amazon using keyword.
     * @param keyword
     * @return returns an empty list if no match can be found.
     */
    public List<Suggestion> getAmazonSuggestions(String keyword){
        String url = "https://www.amazon.com/api/2017/suggestions?mid=ATVPDKIKX0DER&alias=aps&client-info=amazon-search-ui&prefix={value}&suggestion-type=keyword";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("value", keyword);
        RestTemplate restTemplate = new RestTemplate();
        SuggestionResponse suggestions = null;
        try {
            suggestions = restTemplate.getForObject(url,SuggestionResponse.class,uriVariables);
        }catch (Exception e){
            e.printStackTrace();
        }
        return suggestions != null ? suggestions.getSuggestions() : Collections.emptyList();
    }
}
