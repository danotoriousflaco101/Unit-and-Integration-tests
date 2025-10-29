package com.flaco.poll_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatePollRequest {

    @NotBlank(message = "Question cannot be empty!")
    @Size(min = 5, max = 255)
    private String question;

    @NotEmpty(message = "Poll must have options!")
@Size(min = 2, message = "Must have at least two options!")
    private List<String> options;
}
