package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.ChoiceRequestDto;
import com.tallrye.wlearn.security.CurrentUser;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.ChoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "api/choices")
public class ChoiceController {

    private ChoiceService choiceService;

    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @Transactional
    @PostMapping(value = "/")
    public ResponseEntity<ApiResponseDto> createChoice(@CurrentUser UserPrincipal currentUser,
                                                       @Valid @RequestBody ChoiceRequestDto choiceRequestDto) {
        return choiceService.createChoiceByQuestionId(currentUser, choiceRequestDto);
    }

    @Transactional
    @DeleteMapping(value = "/{choiceId}")
    public ResponseEntity<ApiResponseDto> deleteChoice(@CurrentUser UserPrincipal currentUser,
                                                       @PathVariable Long choiceId) {
        return choiceService.deleteChoiceById(currentUser, choiceId);
    }
}