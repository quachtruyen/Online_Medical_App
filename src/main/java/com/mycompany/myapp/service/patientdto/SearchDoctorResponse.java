package com.mycompany.myapp.service.patientdto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchDoctorResponse {

    private Long id;
    private String lastName;
    private String firstName;
    private Integer numberPatients;
    private Integer numberCaredPatients;

    public Integer getNumberCaredPatients() {
        return numberCaredPatients;
    }

    public void setNumberCaredPatients(Integer numberCaredPatients) {
        this.numberCaredPatients = numberCaredPatients;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getNumberPatients() {
        return numberPatients;
    }

    public void setNumberPatients(Integer numberPatients) {
        this.numberPatients = numberPatients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
