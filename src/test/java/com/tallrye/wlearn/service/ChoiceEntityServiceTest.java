package com.tallrye.wlearn.service;

import com.tallrye.wlearn.TestUtils;
import com.tallrye.wlearn.dto.ChoiceRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.entity.QuestionEntity;
import com.tallrye.wlearn.exception.CreatedByException;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.repository.ChoiceRepository;
import com.tallrye.wlearn.repository.QuestionRepository;
import com.tallrye.wlearn.entity.ChoiceEntity;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ChoiceEntityServiceTest extends AbstractServiceTest {

    @Mock
    private ChoiceRepository choiceRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private ConfigurableConversionService smepConversionService;

    @InjectMocks
    private final ChoiceService sut = new ChoiceService(choiceRepository, questionRepository,
            smepConversionService);

    @Test(expected = ResourceNotFoundException.class)
    public void createChoice_ResourceNotFound() {
        //Prepare
        final ChoiceRequestDto request = TestUtils.createDummyChoiceRequest();
        when(questionRepository.findById(request.getQuestionId())).thenReturn(Optional.empty());

        //Test
        sut.createChoiceByQuestionId(currentUser, request);
    }


    @Test(expected = CreatedByException.class)
    public void createChoice_CreatedByException() {
        //Prepare
        final ChoiceRequestDto request = TestUtils.createDummyChoiceRequest();
        final QuestionEntity questionEntity = TestUtils.createDummyQuestion();
        questionEntity.setCreatedBy(1L);
        when(questionRepository.findById(request.getQuestionId())).thenReturn(Optional.of(questionEntity));

        //Test
        sut.createChoiceByQuestionId(currentUser, request);
    }

    @Test
    public void createChoice_Success() {
        //Prepare
        final ChoiceRequestDto request = TestUtils.createDummyChoiceRequest();
        final QuestionEntity questionEntity = TestUtils.createDummyQuestion();
        final ChoiceEntity choiceEntity = TestUtils.createDummyChoice();
        questionEntity.setCreatedBy(currentUser.getId());
        when(questionRepository.findById(request.getQuestionId())).thenReturn(Optional.of(questionEntity));
        when(smepConversionService.convert(request, ChoiceEntity.class)).thenReturn(choiceEntity);

        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.createChoiceByQuestionId(currentUser, request);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void deleteChoice_ResourceNotFound() {
        //Prepare
        when(choiceRepository.findById(0L)).thenReturn(Optional.empty());

        //Test
        sut.deleteChoiceById(currentUser, 0L);
    }


    @Test(expected = CreatedByException.class)
    public void deleteChoice_CreateBy() {
        //Prepare
        final ChoiceEntity choiceEntity = TestUtils.createDummyChoice();
        choiceEntity.setCreatedBy(1L);
        when(choiceRepository.findById(0L)).thenReturn(Optional.of(choiceEntity));

        //Test
        sut.deleteChoiceById(currentUser, 0L);
    }

    @Test
    public void deleteChoice_Success() {
        //Prepare
        final ChoiceEntity choiceEntity = TestUtils.createDummyChoice();
        choiceEntity.setCreatedBy(currentUser.getId());
        when(choiceRepository.findById(0L)).thenReturn(Optional.of(choiceEntity));

        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.deleteChoiceById(currentUser, 0L);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }
}
