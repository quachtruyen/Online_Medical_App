package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.Assignment;
import com.mycompany.myapp.domain.Authority;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import lombok.Data;

public class AssignmentDTO {

    private Long id;

    private Long doctorId;

    private Long patientId;

    private Long userId;

    private Instant assignDate = Instant.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAssignDate(Instant assignDate) {
        this.assignDate = assignDate;
    }

    public Instant getAssignDate() {
        return assignDate;
    }
}
