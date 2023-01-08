package com.example.doormate.domain;

import javax.persistence.*;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    @Column(nullable = false)
    private String groupName;
}
