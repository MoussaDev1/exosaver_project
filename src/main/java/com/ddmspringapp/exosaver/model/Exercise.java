package com.ddmspringapp.exosaver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String solution;

    private String status;

    @Column(name = "feynman_status")
    @Enumerated(EnumType.STRING)
    private FeynmanStatus feynmanStatus = FeynmanStatus.NOT_STARTED;

    @Column(name = "next_review_date")
    private LocalDateTime nextReviewDate;

    @Column(name = "feynman_success_count")
    private int feynmanSuccessCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @OneToMany(mappedBy = "exercise")
    private List<Resource> resources = new ArrayList<>();
}
