package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MedicinePrescription;
import com.mycompany.myapp.domain.Prescription;
import com.mycompany.myapp.repository.MedicinePrescriptionRepository;
import com.mycompany.myapp.repository.PrescriptionRepository;
import com.mycompany.myapp.service.dto.MedicinePrescriptionDTO;
import com.mycompany.myapp.service.dto.PrescriptionDTO;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    private final MedicinePrescriptionRepository medicinePrescriptionRepository;

    public PrescriptionService(
        PrescriptionRepository prescriptionRepository,
        MedicinePrescriptionRepository medicinePrescriptionRepository
    ) {
        this.prescriptionRepository = prescriptionRepository;
        this.medicinePrescriptionRepository = medicinePrescriptionRepository;
    }

    public Prescription create(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = new Prescription();
        prescription.setCreatedAt(Instant.now());
        prescription.setDoctorId(prescriptionDTO.getDoctorId());
        prescription.setUpdatedAt(Instant.now());

        prescription = prescriptionRepository.save(prescription);
        List<Long> idExsits = new ArrayList<>();
        for (MedicinePrescriptionDTO medicinePrescriptionDTO : prescriptionDTO.getMedicineDTOs()) {
            if (medicinePrescriptionDTO.getAmount() == null || medicinePrescriptionDTO.getAmount() <= 0) {
                throw new RuntimeException("Amount không hợp lệ");
            }
            MedicinePrescription medicinePrescription;
            if (medicinePrescriptionDTO.getId() != null) {
                idExsits.add(medicinePrescriptionDTO.getId());
                Optional<MedicinePrescription> medicinePrescriptionOptional = medicinePrescriptionRepository.findById(
                    medicinePrescriptionDTO.getId()
                );
                if (medicinePrescriptionOptional.isPresent()) {
                    medicinePrescription = medicinePrescriptionOptional.get();
                } else {
                    throw new RuntimeException("Không tồn tại thuốc trong đơn thuốc này");
                }
            } else {
                medicinePrescription = new MedicinePrescription();
            }
            medicinePrescription.setMedicineId(medicinePrescriptionDTO.getMedicineId());
            medicinePrescription.setPrescriptionId(prescription.getId());
            medicinePrescription.setAmount(medicinePrescriptionDTO.getAmount());
            medicinePrescription.setPrice(medicinePrescriptionDTO.getPrice());
            medicinePrescription.setDescription(medicinePrescriptionDTO.getDescription());
            medicinePrescriptionRepository.save(medicinePrescription);
        }
        // Xóa thuốc cũ
        medicinePrescriptionRepository.deleteAllByIdIn(idExsits);

        return prescription;
    }

    public void deletePrescription(Long prescriptionId) {
        prescriptionRepository.deleteById(prescriptionId);
    }

    // láy ra danh sách
    public Page<Prescription> getListPrescription(Pageable pageable) {
        Page<Prescription> result = prescriptionRepository.findAll(pageable);
        return result;
    }

    // lấy chi tiết 1
    public Optional<Prescription> findById(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId);
    }
}
