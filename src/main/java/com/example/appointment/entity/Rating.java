package com.example.appointment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    @Id
    private String id = UUID.randomUUID().toString();
    private String appointmentId;
    private String doctorId;
    private Integer rating;
    private String comments;
}
