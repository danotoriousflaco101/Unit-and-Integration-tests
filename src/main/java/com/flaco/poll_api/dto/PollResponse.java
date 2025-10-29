package com.flaco.poll_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor 
public class PollResponse {

    private Long id;
    private String question;
    private List<OptionDTO> options;

    @Data // Lombok: @Getter, @Setter, @ToString, @EqualsAndHashCode
    @AllArgsConstructor 
    public static class OptionDTO {
        private Long id;
        private String text;
        private long voteCount;
    }
}
