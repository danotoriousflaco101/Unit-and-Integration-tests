package com.flaco.poll_api.controller;

import com.flaco.poll_api.dto.CreatePollRequest;
import com.flaco.poll_api.dto.PollResponse;
import com.flaco.poll_api.model.Option;
import com.flaco.poll_api.model.Poll;
import com.flaco.poll_api.service.PollService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollService pollService;

    // Service injection via constructor
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    public ResponseEntity<PollResponse> createPoll(@Valid @RequestBody CreatePollRequest createPollRequest) {
        Poll createdPoll = pollService.createPoll(createPollRequest);
        
        // Convert created Poll to PollResponse DTO and return 201 Created status
        return pollService.getPollById(createdPoll.getId())
                .map(pollResponse -> new ResponseEntity<>(pollResponse, HttpStatus.CREATED))
                .orElse(ResponseEntity.internalServerError().build()); // Fallback, should not happen
    }

    @GetMapping
    public ResponseEntity<List<PollResponse>> getAllPolls() {
        return ResponseEntity.ok(pollService.getAllPolls());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollResponse> getPollById(@PathVariable Long id) {
        return pollService.getPollById(id)
                .map(ResponseEntity::ok) // if found, return 200 OK with PollResponse
                .orElse(ResponseEntity.notFound().build()); // if not found, return 404 Not Found
    }

    @PostMapping("/options/{optionId}/vote")
    public ResponseEntity<?> voteForOption(@PathVariable Long optionId) {
        Optional<Option> updatedOption = pollService.voteForOption(optionId);

        if (updatedOption.isEmpty()) {
            return ResponseEntity.notFound().build(); // Option not found
        }
        
        return ResponseEntity.ok("Vote registered.");
    }
}