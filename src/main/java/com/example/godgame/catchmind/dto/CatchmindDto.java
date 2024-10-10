package com.example.godgame.catchmind.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Getter
@Setter
public class CatchmindDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        @NotBlank
        private int count;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private String status;
        private String currentAnswer;
        private int roundTime;
        private Map<String, Integer> scores;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GuessRequest {
        private String guess;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuessResponse {
        private boolean correct;
        private String message;
    }

}