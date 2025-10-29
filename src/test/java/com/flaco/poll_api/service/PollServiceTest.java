package com.flaco.poll_api.service;

import com.flaco.poll_api.model.Option;
import com.flaco.poll_api.repository.OptionRepository;
import com.flaco.poll_api.repository.PollRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// 1. JUnit to use Mockito ext
@ExtendWith(MockitoExtension.class)
public class PollServiceTest { 

    // 2. @Mock: creates "mock" repo.
    // This object will never talk to real DB.
    @Mock
    private OptionRepository optionRepository;

    // 3. @Mock: creates "mock" repo.
    @Mock
    private PollRepository pollRepository;

    // 4. @InjectMocks: creates real instance of PollService and injects mock repos (@Mock) 
    @InjectMocks
    private PollService pollService;

    // Unit test for voting logic
    @Test
    void testVoteForOption_Success() {
        // --- ARRANGE ---

        // Create fake option for repo return
        Option fakeOption = new Option();
        fakeOption.setId(1L);
        fakeOption.setText("Test Option");
        fakeOption.setVoteCount(0); // Default 0 votes

        // Instruct mock repo: "When findById(1L) method is called you must return Optional containing fakeOption"
        when(optionRepository.findById(1L)).thenReturn(Optional.of(fakeOption));

        // Instruct mock repo: "When save() method is called you return the argument that was passed to it"
        when(optionRepository.save(any(Option.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // --- ACT ---

        // Call the method to test
        Optional<Option> result = pollService.voteForOption(1L);


        // --- ASSERT ---

        // Assert result is not empty
        assertTrue(result.isPresent());
        // Assert vote count is incremented to 1
        assertEquals(1, result.get().getVoteCount());

        // Fundamental Mockito verification: assert save() method on mock repo is called 1 time
        verify(optionRepository, times(1)).save(fakeOption);
    }

    // Second test for "option not found" scenario
    @Test
    void testVoteForOption_NotFound() {
        // --- ARRANGE ---

        // We instruct  mock repository: "When method findById(99L) is called you return an empty Optional"
        when(optionRepository.findById(99L)).thenReturn(Optional.empty());


        // --- ACT ---

        // Call method with a ID unknown 
        Optional<Option> result = pollService.voteForOption(99L);


        // --- ASSERT ---

        // Assert result is empty
        assertTrue(result.isEmpty());

        // Fundamental verification: asser that save() method is NEVER called
        verify(optionRepository, never()).save(any(Option.class));
    }
}