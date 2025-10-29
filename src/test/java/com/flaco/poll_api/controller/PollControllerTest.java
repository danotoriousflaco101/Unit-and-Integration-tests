package com.flaco.poll_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flaco.poll_api.dto.CreatePollRequest;
import com.flaco.poll_api.model.Option;
import com.flaco.poll_api.model.Poll;
import com.flaco.poll_api.repository.OptionRepository;
import com.flaco.poll_api.repository.PollRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 1. @SpringBootTest: load app context for this test
@SpringBootTest
// 2. @AutoConfigureMockMvc: Sets up MockMvc tool to simulate HTTP requests
@AutoConfigureMockMvc
// 3. @Transactional: Rolls back database changes after test runs
@Transactional
public class PollControllerTest {

    // 4. Inject MockMvc tool for simulating HTTP requests.
    @Autowired
    private MockMvc mockMvc;

    // 5. Inject REAL repo (no @Mock).
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionRepository optionRepository;

    // 6. Inject tool to convert Java objects to/from JSON.
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreatePoll_Success() throws Exception {
        // --- ARRANGE ---

        // Create request DTO like client would send
        CreatePollRequest request = new CreatePollRequest();
        request.setQuestion("What is your favorite trick?");
        request.setOptions(List.of("Kickflip", "Treflip", "Bigspin"));

        // Convert Java object into JSON string.
        String requestJson = objectMapper.writeValueAsString(request);

        // --- 2. ACT ---

        // Perform mock POST request to /api/polls.
        mockMvc.perform(post("/api/polls")
                .contentType(MediaType.APPLICATION_JSON) // Set Content type header
                .content(requestJson)) // Set JSON string as request body

        // --- 3. ASSERT ---

                // Assert that HTTP status is 201 Created
                .andExpect(status().isCreated())
                // Assert that JSON contain correct question
                .andExpect(jsonPath("$.question").value("What is your favorite trick?"))
                // Assert response JSON to have 3 options
                .andExpect(jsonPath("$.options.length()").value(3));

        // Verify directly H2 database
        List<Poll> pollsInDb = pollRepository.findAll();
        // Assert poll is actually saved to database.
        assertEquals(1, pollsInDb.size());
        // Assert question in the database is correct.
        assertEquals("What is your favorite trick?", pollsInDb.get(0).getQuestion());
    }

    // --- INTEGRATION TEST FOR VOTE LOGIC ---
    @Test
    void testVoteForOption_Success() throws Exception {
        // --- ARRANGE ---

        // Create and save poll in H2
        Poll poll = new Poll();
        poll.setQuestion("Test Vote");
        
        Option optionToVote = new Option();
        optionToVote.setText("Option 1");
        optionToVote.setVoteCount(0); // Default 0 votes
        
        poll.addOption(optionToVote); // Link option to the poll
        
        // Save poll to REAL DB and get ID of option
        Poll savedPoll = pollRepository.save(poll);
        Long optionId = savedPoll.getOptions().iterator().next().getId();

        // --- ACT ---

        // Simulate POST call to vote endpoint
        mockMvc.perform(post("/api/polls/options/" + optionId + "/vote"))
        
        // --- ASSERT ---

                // assert HTTP response status 200 OK
                .andExpect(status().isOk())
                // assert response body to contain success message
                .andExpect(content().string("Vote registered."));

        // Verify directly in H2 database
        Option votedOption = optionRepository.findById(optionId)
                .orElseThrow(() -> new AssertionError("Option not found in DB"));
        
        // Assert vote count in DB is incremented to 1
        assertEquals(1, votedOption.getVoteCount());
    }
}