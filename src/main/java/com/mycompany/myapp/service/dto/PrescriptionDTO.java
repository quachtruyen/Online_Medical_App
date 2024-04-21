package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Prescription;
import java.time.Instant;
import java.util.List;

public class PrescriptionDTO {

    private Long id;

    private Long doctorId;

    List<MedicinePrescriptionDTO> medicineDTOs;

    private Instant createdAt = Instant.now();

    public PrescriptionDTO() {}

    public PrescriptionDTO(Prescription prescription) {
        this.id = prescription.getId();
        this.doctorId = prescription.getDoctorId();
        this.createdAt = prescription.getCreatedAt();
    }

    public List<MedicinePrescriptionDTO> getMedicineDTOs() {
        return medicineDTOs;
    }

    public void setMedicineDTOs(List<MedicinePrescriptionDTO> medicineDTOs) {
        this.medicineDTOs = medicineDTOs;
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

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
