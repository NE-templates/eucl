package com.trex.eucl.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "meters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, length = 6, nullable = false)
    private String meterNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

}
