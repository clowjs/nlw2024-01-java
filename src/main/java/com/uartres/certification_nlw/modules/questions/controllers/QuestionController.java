package com.uartres.certification_nlw.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uartres.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import com.uartres.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.uartres.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.uartres.certification_nlw.modules.questions.entities.QuestionEntity;
import com.uartres.certification_nlw.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var result = questionRepository.findByTechnology(technology);

        var toMap = result.stream().map(question -> mapQuestionToDTO(question))
                .collect(Collectors.toList());

        return toMap;
    }

    static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
        var questionResultDTO = QuestionResultDTO.builder()
                .id(question.getId())
                .technology(question.getTechnology())
                .description(question.getDescription()).build();

        List<AlternativesResultDTO> alternatives = question.getAlternatives()
                .stream().map(alternative -> mapAlternativeToDTO(alternative))
                .collect(Collectors.toList());

        questionResultDTO.setAlternatives(alternatives);

        return questionResultDTO;
    };

    static AlternativesResultDTO mapAlternativeToDTO(AlternativesEntity alternative) {
        return AlternativesResultDTO.builder()
                .id(alternative.getId())
                .description(alternative.getDescription())
                .build();

    }
}
