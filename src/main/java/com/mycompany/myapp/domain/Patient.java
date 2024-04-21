package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "patient")
@Getter
@Setter
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String address;

    private String healthStatus;

    private String diseaseSymptoms;

    private String prescription;

    private String imageCertificate;

    public Patient(Patient patient) {
        this.userId = patient.getUserId();
        this.prescription = patient.getPrescription();
        this.id = patient.id;
        this.address = patient.address;
        this.diseaseSymptoms = patient.getDiseaseSymptoms();
        this.healthStatus = patient.getHealthStatus();
        this.imageCertificate = patient.getImageCertificate();
    }

    public Patient() {}

    public String getImageCertificate() {
        return imageCertificate;
    }

    public void setImageCertificate(String imageCertificate) {
        this.imageCertificate = imageCertificate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getDiseaseSymptoms() {
        return diseaseSymptoms;
    }

    public void setDiseaseSymptoms(String diseaseSymptoms) {
        this.diseaseSymptoms = diseaseSymptoms;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}
