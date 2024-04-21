package com.mycompany.myapp.service.doctordto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorSearchPatientRequest {

    private Long doctorId;
    private String patientStatus;
    private Integer limit;
    private Long offset;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        this.patientStatus = patientStatus;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
