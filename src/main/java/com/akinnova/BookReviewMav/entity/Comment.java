package com.akinnova.BookReviewMav.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "comment_table")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String comment;
    private String username;
    @CreationTimestamp
    private LocalDateTime commentTime;

    //Relationship with book
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_comments",
            joinColumns = @JoinColumn(name = "comment", referencedColumnName = "comment"),
            inverseJoinColumns = @JoinColumn(name = "book", referencedColumnName = "title")
    )
    private List<BookEntity> books;
}
