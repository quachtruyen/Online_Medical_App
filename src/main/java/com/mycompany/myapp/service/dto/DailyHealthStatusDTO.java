package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.DailyHealthStatus;
import java.time.Instant;

public class DailyHealthStatusDTO {

    private Long id;

    private Long patientId;

    private Instant updatedAt = Instant.now();

    private Instant date = Instant.now();

    private String healthStatus;

    private String symptoms;

    private String notes;

    private Long doctorActionId;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DailyHealthStatusDTO() {}

    public DailyHealthStatusDTO(DailyHealthStatus dailyHealthStatus) {
        this.id = dailyHealthStatus.getId();
        this.symptoms = dailyHealthStatus.getSymptoms();
        this.notes = dailyHealthStatus.getNotes();
        this.date = dailyHealthStatus.getDate();
        this.updatedAt = dailyHealthStatus.getUpdatedAt();
        this.patientId = dailyHealthStatus.getPatientId();
        this.healthStatus = dailyHealthStatus.getHealthStatus();
        this.image = dailyHealthStatus.getImage();
    }

    public Long getDoctorActionId() {
        return doctorActionId;
    }

    public void setDoctorActionId(Long doctorActionId) {
        this.doctorActionId = doctorActionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Instant getDate() {
        return date;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }
}
