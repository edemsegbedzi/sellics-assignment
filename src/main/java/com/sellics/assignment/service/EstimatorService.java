package com.sellics.assignment.service;

import com.sellics.assignment.dto.Suggestion;
import com.sellics.assignment.dto.SuggestionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstimatorService {


    /**
     * Gets a suggestions from amazon using keyword.
     * @param keyword
     * @return returns String of suggested keywords else returns an empty list if no match can be found.
     */
    public List<String> getAmazonSuggestions(String keyword){
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

        return suggestions != null ? suggestions.getSuggestions().stream().map(Suggestion::getValue).collect(Collectors.toList()) : Collections.emptyList();
    }



    public Double computeScore(String keyword){
        //Use string buffer as it is mutable and thread safe.
        StringBuffer value = new StringBuffer();

        // Convert String to list of Characters
        List<Character> keywordList = keyword.chars().mapToObj(e->(char)e).collect(Collectors.toList());

        int score = 0;
        List<String> suggestionList = null;
        for(int i=0;i < keywordList.size();i++){
            value.append(keywordList.get(i));
            suggestionList = getAmazonSuggestions(value.toString());
            int x = computeScoreByPosition(keyword,suggestionList); //returns score based on position of keyword in suggested list
            score +=x;
            // Break out of loop when by appending an additional character in the list gives the keyword the highest score
            if(x==suggestionList.size()){
                //Since first i characters of keyword is at the top, the remaining characters would also be at the top hence
                //compute the scores using the max attainable score per character of the remaining keyword.
                score += (suggestionList.size())*(keyword.length()-i-1);
                break;
            }

        }
        //Return score as percentage.
        return score/(Double.valueOf(suggestionList.size())*keyword.length())*100;


        // The above loop has been written using java 8 streams in the code below,  however this approach was slow when the early characters of the keyword
        // attained the highest possible score.

//       return keyword.chars().mapToObj(e->(char)e).mapToDouble(e->{
//            value.append(e);
//            List<String> suggestionList = getAmazonSuggestions(value.toString());
//            return computeScoreByPosition(keyword,suggestionList)/ (Double.valueOf(keyword.length())*suggestionList.size());
//        }).sum() *100 ;
    }


    /**
     * Computes the score of a keyword using it's position in the list of suggestions.
     * @param word keyword or substring of keyword
     * @param suggestions suggestions based of keyword or keyword's substring
     * @return
     */
    public int computeScoreByPosition(String word, List<String> suggestions){
        int position = suggestions.size();
        for(int i=0; i< suggestions.size();i++){
            if(suggestions.get(i).equals(word)){
                position = i;
                break;
            }
        }
        return suggestions.size() - position;
    }

}
