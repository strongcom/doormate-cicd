package com.example.doormate.dto;

import com.example.doormate.domain.Reminder;
import com.example.doormate.domain.RepetitionPeriod;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReminderDto {

    private String title;

    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime endTime;

    private RepetitionPeriod repetitionPeriod;  // 반복주기(매일, 매주, 매월, 매년)

    private String repetitionDay;   // 반복 주기(요일별)

    public ReminderDto(String title, String content, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reminder toReminder(ReminderDto reminderDto) {
        return Reminder.builder()
                .title(this.title)
                .content(this.content)
                .subTitle(toSubtitle(reminderDto))
                .startDate(this.startDate)
                .endDate(this.endDate)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .repetitionPeriod(this.repetitionPeriod)
                .repetitionDay(this.repetitionDay)
                .build();
    }

    public String toSubtitle(ReminderDto reminderDto) {
        String repetition;
        if (reminderDto.getRepetitionPeriod() == RepetitionPeriod.DAILY) {
            repetition = "매일";
        } else if (reminderDto.getRepetitionPeriod() == RepetitionPeriod.WEEKLY) {
            repetition = "매주 " + repetitionDay;
        } else if (reminderDto.getRepetitionPeriod() == RepetitionPeriod.MONTHLY) {
            repetition = "매달";
        } else if (reminderDto.getRepetitionPeriod() == RepetitionPeriod.YEARLY) {
            repetition = "매년";
        } else {
            repetition = startDate.toString();
        }
        return "알람, " + repetition + " " + this.startTime + " - " + this.endTime + " 사이 알림";
    }
}
