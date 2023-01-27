package com.example.doormate.dto;

import com.example.doormate.domain.RepetitionDay;
import com.example.doormate.domain.RepetitionPeriod;
import com.example.doormate.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReminderResponseDto {
    private long reminderId;

    private User userId;

    private String title;

    private String subTitle;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int repetitionId;

    private RepetitionPeriod repetitionPeriod;

    private String repetitionCycle;

    private RepetitionDay repetitionDay;
}
