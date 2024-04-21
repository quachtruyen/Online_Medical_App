package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doctor_action")
public class DoctorAction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;

    private Long dailyHealthStatusId;

    private Long prescriptionId;

    private String advise;

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDailyHealthStatusId(Long dailyHealthStatusId) {
        this.dailyHealthStatusId = dailyHealthStatusId;
    }

    public Long getDailyHealthStatusId() {
        return dailyHealthStatusId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public String getAdvise() {
        return advise;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setNote(String notes) {
        this.note = notes;
    }

    public String getNote() {
        return note;
    }
}
