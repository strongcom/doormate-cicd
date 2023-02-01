package com.example.doormate.dto;

import com.example.doormate.domain.Reminder;
import com.example.doormate.domain.RepetitionPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReminderDto {

    private String title;

    private String content;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime endDate;

    private RepetitionPeriod repetitionPeriod;  // 반복주기(매일, 매주, 매월, 매년)

    private String repetitionDay;   // 반복 주기(요일별)

    public Reminder toEntity(ReminderDto reminderRequestDto) {
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
