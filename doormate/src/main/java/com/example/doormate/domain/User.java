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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private LocalDateTime joinDate;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reminder reminderId;



}
