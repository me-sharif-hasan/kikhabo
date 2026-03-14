package com.iishanto.kikhabo.infrastructure.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "recipe_bookmark",
    uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "recipeId"}),
    indexes = @Index(name = "idx_bookmark_user", columnList = "userId")
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    /** MongoDB ObjectId of the recipe. */
    @Column(nullable = false)
    private String recipeId;

    @Builder.Default
    private LocalDateTime bookmarkedAt = LocalDateTime.now();
}
