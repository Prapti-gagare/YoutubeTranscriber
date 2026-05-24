package com.youtube.transcriber.repository;

import com.youtube.transcriber.entity.Conversion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionRepository
        extends JpaRepository<Conversion, Long> {

    List<Conversion> findAllByOrderByCreatedAtDesc();

    List<Conversion> findByLanguage(String language);
}