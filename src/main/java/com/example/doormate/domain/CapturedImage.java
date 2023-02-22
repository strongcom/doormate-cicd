package com.example.doormate.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CapturedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long captureId;

    @Column(nullable = false)
    private String captureImage;

    @Column(nullable = false)
    private LocalDateTime captureTime;

    @Column(nullable = false)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

}
