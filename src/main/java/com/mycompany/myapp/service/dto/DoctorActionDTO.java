package com.mycompany.myapp.service.dto;

import java.time.Instant;
import java.util.List;
import javax.validation.constraints.NotBlank;

public class DoctorActionDTO {

    private Long id;

    private Long doctorId;

    private Long dailyHealthStatusId;

    private Long prescriptionId;

    @NotBlank
    private String advise;

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    private String note;

    private PrescriptionDTO prescription;

    List<MedicinePrescriptionDTO> medicines;

    public PrescriptionDTO getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionDTO prescription) {
        this.prescription = prescription;
    }

    public List<MedicinePrescriptionDTO> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicinePrescriptionDTO> medicines) {
        this.medicines = medicines;
    }

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

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
