package com.akinnova.BookReviewMav.entity;

import com.akinnova.BookReviewMav.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "project_table",
        uniqueConstraints = @UniqueConstraint(columnNames = "projectId")
)

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String coverImage;
    private String title;
    private Category category;
    private String content;
    private String projectId;
    private String clientUsername;
    private String serviceProviderUsername;
    @Enumerated(EnumType.STRING)
    private JobAcceptanceStatus jobAcceptanceStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private ProjectStartApproval projectStartApproval;
    @Enumerated(EnumType.STRING)
    private ProjectLevelApproval projectLevelApproval;
    @Enumerated(EnumType.STRING)
    private ProjectCompletion projectCompletion;
    private Boolean activeStatus;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime modifiedOn;

}
