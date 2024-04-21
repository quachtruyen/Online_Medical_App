package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.MedicinePrescription;

public class MedicinePrescriptionDTO {

    private Long id;

    private Long medicineId;

    private Long prescriptionId;

    private String description;

    private Integer price;

    private Integer amount;

    private MedicineDTO medicine;

    public MedicinePrescriptionDTO() {}

    public MedicinePrescriptionDTO(MedicinePrescription medicinePrescription) {
        this.id = medicinePrescription.getId();
        this.medicineId = medicinePrescription.getMedicineId();
        this.prescriptionId = medicinePrescription.getPrescriptionId();
        this.amount = medicinePrescription.getAmount();
        this.price = medicinePrescription.getPrice();
        this.description = medicinePrescription.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public MedicineDTO getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineDTO medicine) {
        this.medicine = medicine;
    }
}
