package com.parlor.booking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    private Long id;
    private int rate;

    @NotNull(message = "Feedback should not be empty")
    @Length(min = 1, max = 2000)
    private String text;

    private Long clientId;
    private String specialistName;
    private LocalDateTime createdOn;

}
