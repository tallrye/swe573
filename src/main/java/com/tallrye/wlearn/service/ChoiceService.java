package com.tallrye.wlearn.service.implementation;

import com.tallrye.wlearn.dto.ChoiceRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.entity.QuestionEntity;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.persistence.ChoiceRepository;
import com.tallrye.wlearn.persistence.QuestionRepository;
import com.tallrye.wlearn.entity.ChoiceEntity;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.util.WlearnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChoiceService {

    private ChoiceRepository choiceRepository;

    private QuestionRepository questionRepository;

    private ConfigurableConversionService smepConversionService;

    public ChoiceService(ChoiceRepository choiceRepository, QuestionRepository questionRepository,
                         ConfigurableConversionService smepConversionService) {
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
        this.smepConversionService = smepConversionService;
    }


    public ResponseEntity<ApiResponseDto> createChoiceByQuestionId(UserPrincipal currentUser,
                                                                   ChoiceRequestDto choiceRequestDto) {

        final QuestionEntity questionEntity = questionRepository.findById(choiceRequestDto.getQuestionId()).orElseThrow(
                () -> new ResourceNotFoundException("QuestionEntity", "id", choiceRequestDto.getQuestionId().toString()));

        WlearnUtils.checkCreatedBy("QuestionEntity", currentUser.getId(), questionEntity.getCreatedBy());

        final ChoiceEntity choiceEntity = smepConversionService.convert(choiceRequestDto, ChoiceEntity.class);
        choiceEntity.setQuestionEntity(questionEntity);
        choiceRepository.save(choiceEntity);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "ChoiceEntity created successfully"));
    }


    public ResponseEntity<ApiResponseDto> deleteChoiceById(UserPrincipal currentUser, Long choiceId) {

        final ChoiceEntity choiceEntity = choiceRepository.findById(choiceId).orElseThrow(
                () -> new ResourceNotFoundException("ChoiceEntity", "id", choiceId.toString()));

        WlearnUtils.checkCreatedBy("ChoiceEntity", currentUser.getId(), choiceEntity.getCreatedBy());

        choiceRepository.delete(choiceEntity);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "ChoiceEntity deleted successfully"));
    }
}
