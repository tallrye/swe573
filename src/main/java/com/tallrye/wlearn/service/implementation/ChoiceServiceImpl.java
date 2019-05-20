package com.tallrye.wlearn.service.implementation;

import com.tallrye.wlearn.controller.dto.request.ChoiceRequest;
import com.tallrye.wlearn.controller.dto.response.ApiResponse;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.persistence.ChoiceRepository;
import com.tallrye.wlearn.persistence.QuestionRepository;
import com.tallrye.wlearn.persistence.model.Choice;
import com.tallrye.wlearn.persistence.model.Question;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.ChoiceService;
import com.tallrye.wlearn.service.util.SmeptUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChoiceServiceImpl implements ChoiceService {

    private ChoiceRepository choiceRepository;

    private QuestionRepository questionRepository;

    private ConfigurableConversionService smepConversionService;

    public ChoiceServiceImpl(ChoiceRepository choiceRepository, QuestionRepository questionRepository,
            ConfigurableConversionService smepConversionService) {
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
        this.smepConversionService = smepConversionService;
    }

    @Override
    public ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser,
            ChoiceRequest choiceRequest) {

        final Question question = questionRepository.findById(choiceRequest.getQuestionId()).orElseThrow(
                () -> new ResourceNotFoundException("Question", "id", choiceRequest.getQuestionId().toString()));

        SmeptUtilities.checkCreatedBy("Question", currentUser.getId(), question.getCreatedBy());

        final Choice choice = smepConversionService.convert(choiceRequest, Choice.class);
        choice.setQuestion(question);
        choiceRepository.save(choice);
        return ResponseEntity.ok().body(new ApiResponse(true, "Choice created successfully"));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteChoiceById(UserPrincipal currentUser, Long choiceId) {

        final Choice choice = choiceRepository.findById(choiceId).orElseThrow(
                () -> new ResourceNotFoundException("Choice", "id", choiceId.toString()));

        SmeptUtilities.checkCreatedBy("Choice", currentUser.getId(), choice.getCreatedBy());

        choiceRepository.delete(choice);
        return ResponseEntity.ok().body(new ApiResponse(true, "Choice deleted successfully"));
    }
}
