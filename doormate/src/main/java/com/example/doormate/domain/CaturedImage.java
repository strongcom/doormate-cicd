package com.example.doormate.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CaturedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long catureId;

    @Column(nullable = false)
    private String catureImage;

    @Column(nullable = false)
    private LocalDateTime catureTime;

    @Column(nullable = false)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

}
