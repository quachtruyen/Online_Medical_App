package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Doctor;
import com.mycompany.myapp.domain.DoctorAction;
import com.mycompany.myapp.domain.Prescription;
import com.mycompany.myapp.service.DoctorActionService;
import com.mycompany.myapp.service.PrescriptionService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.dto.DoctorActionDTO;
import com.mycompany.myapp.service.dto.PrescriptionDTO;
import java.time.Instant;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor-action")
public class DoctorActionResource {

    private final DoctorActionService doctorActionService;
    private final PrescriptionService prescriptionService;
    private final UserService userService;

    public DoctorActionResource(DoctorActionService doctorActionService, PrescriptionService prescriptionService, UserService userService) {
        this.doctorActionService = doctorActionService;
        this.prescriptionService = prescriptionService;
        this.userService = userService;
    }

    /**
     * Doctor chỉ cho tạo mới, nếu sai muốn sửa thì phải tạo mới doctor action cho daily health status đó
     * @param doctorActionDTO
     */
    @PostMapping("")
    public void createDoctorAction(@RequestBody DoctorActionDTO doctorActionDTO) {
        Doctor doctor = userService.getCurrentUserIfIsDoctor();
        doctorActionDTO.setDoctorId(doctor.getId());
        doctorActionDTO.setCreatedAt(Instant.now());
        // Nếu có kê đơn thuốc
        if (!CollectionUtils.isEmpty(doctorActionDTO.getMedicines())) {
            PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
            prescriptionDTO.setId(doctorActionDTO.getPrescriptionId());
            prescriptionDTO.setDoctorId(doctor.getId());
            prescriptionDTO.setMedicineDTOs(doctorActionDTO.getMedicines());
            Prescription prescription = prescriptionService.create(prescriptionDTO);
            if (prescription != null) {
                doctorActionDTO.setPrescriptionId(prescription.getId());
            }
        }
        doctorActionService.create(doctorActionDTO);
    }

    @GetMapping("/by-health-status/{healthStatusId}")
    public DoctorAction getDoctorAction(@PathVariable Long healthStatusId) {
        return doctorActionService.findByDailyHealthStatusId(healthStatusId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorAction> getDoctorActions(@PathVariable Long id) {
        return ResponseEntity.ok(doctorActionService.findById(id));
    }
}
