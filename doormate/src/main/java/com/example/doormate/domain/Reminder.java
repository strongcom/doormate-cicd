package com.example.doormate.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long reminderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @Column(nullable = false)
    private String title;

    private String subTitle;

    private String content;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate;


    private int repetitionId;

    private RepetitionPeriod repetitionPeriod;

    private String repetitionDay ;

    @Builder
    public Reminder(String title, String content,
                    RepetitionPeriod repetitionPeriod, String repetitionDay) {
        this.title = title;
        this.content = content;
        this.repetitionPeriod = repetitionPeriod;
        this.repetitionDay = repetitionDay;
    }

    @Builder
    public Reminder(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
