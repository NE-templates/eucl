package com.trex.eucl.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    private String message;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime issueDate;

}
