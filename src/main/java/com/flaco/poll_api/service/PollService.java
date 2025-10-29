package com.flaco.poll_api.service;

import com.flaco.poll_api.dto.CreatePollRequest;
import com.flaco.poll_api.dto.PollResponse;
import com.flaco.poll_api.model.Option;
import com.flaco.poll_api.model.Poll;
import com.flaco.poll_api.repository.OptionRepository;
import com.flaco.poll_api.repository.PollRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final OptionRepository optionRepository;

    // Constructor injection for dependency injection
    public PollService(PollRepository pollRepository, OptionRepository optionRepository) {
        this.pollRepository = pollRepository;
        this.optionRepository = optionRepository;
    }

    // Finds all polls and converts to response DTOs.
    public List<PollResponse> getAllPolls() {
        return pollRepository.findAll().stream()
                .map(this::mapPollToResponse)
                .collect(Collectors.toList());
    }

    // Finds single poll by ID and converts to DTO.
    public Optional<PollResponse> getPollById(Long id) {
        return pollRepository.findById(id).map(this::mapPollToResponse);
    }

    // Creates new poll from DTO request.
    @Transactional
    public Poll createPoll(CreatePollRequest createPollRequest) {
        Poll poll = new Poll();
        poll.setQuestion(createPollRequest.getQuestion());

        for (String optionText : createPollRequest.getOptions()) {
            Option option = new Option();
            option.setText(optionText);
            poll.addOption(option); // Helper method to link option to poll
        }
        return pollRepository.save(poll);
    }

    // Contains business logic for voting.
    // Find option, increments counter and save.
    @Transactional
    public Optional<Option> voteForOption(Long optionId) {
        Optional<Option> optionalOption = optionRepository.findById(optionId);
        if (optionalOption.isEmpty()) {
            return Optional.empty(); // Returns empty if option doesn't exist
        }

        Option option = optionalOption.get();
        option.setVoteCount(option.getVoteCount() + 1); // Increment counter
        Option savedOption = optionRepository.save(option);
        
        return Optional.of(savedOption);
    }

    // --- Private Helper Methods for Mapping ---

    // Private method to convert Poll entity into PollResponse DTO
    private PollResponse mapPollToResponse(Poll poll) {
        List<PollResponse.OptionDTO> optionDTOs = poll.getOptions().stream()
                .map(this::mapOptionToDTO)
                .collect(Collectors.toList());

        return new PollResponse(poll.getId(), poll.getQuestion(), optionDTOs);
    }

    // Private method to convert Option entity into OptionDTO
    private PollResponse.OptionDTO mapOptionToDTO(Option option) {
        return new PollResponse.OptionDTO(option.getId(), option.getText(), option.getVoteCount());
    }
}