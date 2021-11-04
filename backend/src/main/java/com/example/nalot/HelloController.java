package com.example.nalot;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    @Autowired
    JavaSparkContext sc;

    @GetMapping("/api/hello")
    public String hello(){
        return "안녕하세요. 현재 서버시간은 "+new Date() +"입니다. \n";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/wordcount")
    public Map<String, Long> count(@RequestBody String words) {
        System.out.println("단어:" + words);
        List<String> wordList = Arrays.asList(words.split("\\|"));

        JavaRDD<String> words1 = sc.parallelize(wordList);
        Map<String, Long> wordCounts = words1.countByValue();
        return wordCounts;
    }
}