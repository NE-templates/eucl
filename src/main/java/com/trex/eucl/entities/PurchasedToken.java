package com.trex.eucl.entities;

import com.trex.eucl.enums.TokenStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchased_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PurchasedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    @Column(length = 16, unique = true, nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_status")
    private TokenStatus tokenStatus;

    @Column(name = "token_value_days", nullable = false, length = 11)
    private Integer tokenValueDays;

    @Column(name = "purchase_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime purchaseDate;

    @Column(nullable = false, length = 11)
    private Integer amount;

}
