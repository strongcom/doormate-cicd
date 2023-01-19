package com.example.doormate.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private String content;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private boolean repetitionYN;

    private int repetitionId;

    private RepetitionDay repetitionDay;

    private RepetitionPeriod repetitionPeriod;

}
