package com.example.doormate.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reminderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(nullable = false)
    private String title;

    private String subTitle;

    private String content;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private RepetitionPeriod repetitionPeriod;

    private String repetitionDay;

    @Builder
    public Reminder(String title, String content, String subTitle,
                    LocalTime startTime, LocalTime endTime,
                    LocalDate startDate, LocalDate endDate,
                    RepetitionPeriod repetitionPeriod, String repetitionDay) {
        this.title = title;
        this.content = content;
        this.subTitle = subTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repetitionPeriod = repetitionPeriod;
        this.repetitionDay = repetitionDay;
    }

    public Alarm toAlarm(Reminder reminder) {
        return Alarm.builder()
                .reminder(reminder)
                .noticeDate(reminder.getStartDate())
                .startTime(reminder.getStartTime())
                .endTime(reminder.getEndTime())
                .build();
    }
}
