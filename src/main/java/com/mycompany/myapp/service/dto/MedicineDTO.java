package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Medicine;

public class MedicineDTO {

    private Long id;

    private String name;

    private String origin;

    private String element;

    private String uses;

    private Integer price;

    private Integer amount;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MedicineDTO(Medicine medicine) {
        this.id = medicine.getId();
        this.element = medicine.getElement();
        this.name = medicine.getName();
        this.origin = medicine.getOrigin();
        this.price = medicine.getPrice();
        this.uses = medicine.getUses();
        this.image = medicine.getImage();
    }

    public MedicineDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public String getUses() {
        return uses;
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
}
