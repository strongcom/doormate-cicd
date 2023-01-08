package com.example.doormate.domain;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name="groupId")
    private Group group;



}
