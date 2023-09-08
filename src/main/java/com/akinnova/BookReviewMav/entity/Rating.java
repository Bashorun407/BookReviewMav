package com.akinnova.BookReviewMav.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "rating_table")
public class Rating{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_provider_name", referencedColumnName = "username")
    private UserEntity username;
    private Integer starRating;
    private Long rateCount;
    private Double averageRating;
    private LocalDateTime rateTime;

}
