package com.asn.smarthelmetproject.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double fare;

    @ManyToOne
    private User user;

    @ManyToOne
    private Helmet helmet;

    private boolean isComplete;  // Ensure the field is there

    // Setters and Getters for isComplete

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean isComplete() {
        return isComplete;
    }

    // Constructor if needed
    public Transaction() {
    }

    public long getRideDurationInMinutes() {
        if (startTime != null && endTime != null) {
            return java.time.Duration.between(startTime, endTime).toMinutes();
        }
        return 0;
    }

    public Transaction(User user, Helmet helmet, LocalDateTime startTime, String code) {
        this.user = user;
        this.helmet = helmet;
        this.startTime = startTime;
        this.code = code;
        this.isComplete = false;  // Ride hasn't finished yet
    }

}
