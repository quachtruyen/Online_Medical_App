package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Prescription;
import com.mycompany.myapp.repository.MedicinePrescriptionRepository;
import com.mycompany.myapp.service.MedicineService;
import com.mycompany.myapp.service.PrescriptionService;
import com.mycompany.myapp.service.dto.MedicinePrescriptionDTO;
import com.mycompany.myapp.service.dto.PrescriptionDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescription")
public class PrescriptionResource {

    private final PrescriptionService prescriptionService;

    private final MedicineService medicineService;

    private final MedicinePrescriptionRepository medicinePrescriptionRepository;

    public PrescriptionResource(
        PrescriptionService prescriptionService,
        MedicineService medicineService,
        MedicinePrescriptionRepository medicinePrescriptionRepository
    ) {
        this.prescriptionService = prescriptionService;
        this.medicineService = medicineService;
        this.medicinePrescriptionRepository = medicinePrescriptionRepository;
    }

    @GetMapping("/{id}")
    public PrescriptionDTO getById(@PathVariable Long id) {
        Optional<Prescription> prescription = prescriptionService.findById(id);
        if (prescription.isPresent()) {
            PrescriptionDTO prescriptionDTO = new PrescriptionDTO(prescription.get());
            List<MedicinePrescriptionDTO> medicinePrescriptionDTOS = medicinePrescriptionRepository
                .findAllByPrescriptionId(id)
                .stream()
                .map(MedicinePrescriptionDTO::new)
                .collect(Collectors.toList());
            medicinePrescriptionDTOS.forEach(elm -> elm.setMedicine(medicineService.findDTOById(elm.getMedicineId())));
            prescriptionDTO.setMedicineDTOs(medicinePrescriptionDTOS);
            return prescriptionDTO;
        }
        throw new RuntimeException("Không tìm thấy đơn thuốc");
    }
}
