package com.dmitrjch.task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Task task;

    private String comment;

    private String authorId;
}