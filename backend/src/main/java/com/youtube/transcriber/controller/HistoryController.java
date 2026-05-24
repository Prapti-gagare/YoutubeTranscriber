package com.youtube.transcriber.controller;

import com.youtube.transcriber.entity.Conversion;
import com.youtube.transcriber.repository.ConversionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HistoryController {

    @Autowired
    private ConversionRepository conversionRepository;

    @GetMapping("/history")
    public List<Conversion> getHistory() {

       return conversionRepository
        .findAllByOrderByCreatedAtDesc();
    }
}