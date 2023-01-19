package com.example.doormate.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long reminderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subTitle;

    private String content;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private int repetitionId;

    private RepetitionDay repetitionDay;

    private RepetitionPeriod repetitionPeriod;

    @Builder
    public Reminder(long reminderId, User userId,
                    String title, String content,
                    LocalDateTime startDate, LocalDateTime endDate,
                    boolean repetitionYN, int repetitionId,
                    RepetitionDay repetitionDay, RepetitionPeriod repetitionPeriod) {
        this.reminderId = reminderId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repetitionId = repetitionId;
        this.repetitionDay = repetitionDay;
        this.repetitionPeriod = repetitionPeriod;
    }
}
