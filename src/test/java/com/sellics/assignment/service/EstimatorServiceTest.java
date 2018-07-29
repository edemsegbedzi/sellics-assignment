package com.sellics.assignment.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EstimatorServiceTest {
    @Autowired
    EstimatorService estimatorService;

    @Test
    public void computeWithKeywordLaptop(){
        assertEquals(java.util.Optional.ofNullable(59),java.util.Optional.ofNullable(estimatorService.computeScore("laptop")));
    }

}