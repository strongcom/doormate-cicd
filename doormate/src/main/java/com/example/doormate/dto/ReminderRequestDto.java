package com.example.doormate.dto;

import com.example.doormate.domain.Reminder;
import com.example.doormate.domain.RepetitionPeriod;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReminderRequestDto {

    private String title;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

    private RepetitionPeriod repetitionPeriod;  // 반복주기(매일, 매주, 매월, 매년)

    private String repetitionDay;   // 반복 주기(요일별)

    public Reminder toEntity(ReminderRequestDto reminderRequestDto) {
        return Reminder.builder()
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .repetitionPeriod(repetitionPeriod)
                .repetitionDay(repetitionDay)
                .build();
    }
}
