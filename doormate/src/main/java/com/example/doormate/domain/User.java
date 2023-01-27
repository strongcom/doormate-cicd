package com.example.doormate.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime joinDate;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Reminder reminderId;

    @Builder
    public User(Long userId, String username, String password,
                String nickname, LocalDateTime joinDate, LocalDateTime lastLogin) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
    }
}
