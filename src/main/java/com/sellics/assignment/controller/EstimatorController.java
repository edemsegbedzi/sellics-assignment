package com.sellics.assignment.controller;


import com.sellics.assignment.dto.Score;
import com.sellics.assignment.service.EstimatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstimatorController {
    @Autowired
    EstimatorService estimatorService;


    @GetMapping("estimate")
    public Score suggestions(@RequestParam  String keyword){
        return  new Score(keyword, estimatorService.computeScore(keyword));
    }
}
