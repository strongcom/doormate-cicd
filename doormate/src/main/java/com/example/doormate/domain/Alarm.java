package com.example.doormate.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reminder_id")
    private Reminder reminder;

    private LocalDate noticeDate;

    private LocalTime startTime;

    private LocalTime endTime;

    /**
     * 연관관계 설정
     */
    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }


    @Builder
    public Alarm(Reminder reminder, LocalDate noticeDate, LocalTime startTime, LocalTime endTime) {
        this.reminder = reminder;
        this.noticeDate = noticeDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
