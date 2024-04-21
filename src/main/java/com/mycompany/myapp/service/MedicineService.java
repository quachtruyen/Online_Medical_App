package com.mycompany.myapp.service;

import com.mycompany.myapp.config.ApplicationProperties;
import com.mycompany.myapp.domain.Medicine;
import com.mycompany.myapp.repository.MedicineRepository;
import com.mycompany.myapp.service.dto.MedicineDTO;
import java.io.File;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Quản lý thuốc
 */
@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    private final ApplicationProperties applicationProperties;

    public MedicineService(MedicineRepository medicineRepository, ApplicationProperties applicationProperties) {
        this.medicineRepository = medicineRepository;
        this.applicationProperties = applicationProperties;
    }

    public Medicine createOrUpdate(MedicineDTO medicineDTO) {
        Medicine medicine = new Medicine();

        if (medicineDTO.getId() != null) {
            Optional<Medicine> medicineOptional = medicineRepository.findById(medicineDTO.getId());
            if (medicineOptional.isEmpty()) {
                throw new RuntimeException("Không tồn tại tên thuốc này");
            }
            medicine = medicineOptional.get();
        } else { // tạo mới nếu chưa tồn tại
            medicine = new Medicine();
        }
        medicine.setName(medicineDTO.getName());
        medicine.setOrigin(medicineDTO.getOrigin());
        medicine.setElement(medicineDTO.getElement());
        medicine.setUses(medicineDTO.getUses());
        medicine.setPrice(medicineDTO.getPrice());

        return medicineRepository.save(medicine);
    }

    public void createOrUpdateImage(Long id, String image) {
        Medicine medicine;

        Optional<Medicine> medicineOptional = medicineRepository.findById(id);
        if (medicineOptional.isEmpty()) {
            throw new RuntimeException("Không tồn tại thuốc này");
        }
        medicine = medicineOptional.get();

        if (StringUtils.isNotBlank(image)) {
            medicine.setImage(image);
            medicineRepository.save(medicine);
        }
    }

    public void deleteMedicine(Long medicineId) {
        Optional<Medicine> medicine = medicineRepository.findById(medicineId);
        if (medicine.isEmpty()) {
            throw new RuntimeException("Không tồn tại thuốc id này");
        }
        medicine.get().setDeleted(true);
        medicineRepository.save(medicine.get());
    }

    // láy ra danh sách
    public Page<Medicine> getListMedicine(Pageable pageable, String search, boolean isAdmin) {
        if (isAdmin) {
            if (StringUtils.isNotBlank(search)) {
                return medicineRepository.findAllByNameContainingIgnoreCase(pageable, search);
            }
            return medicineRepository.findAll(pageable);
        } else {
            if (StringUtils.isNotBlank(search)) {
                return medicineRepository.findAllByNameContainingIgnoreCaseAndDeletedIsFalse(pageable, search);
            }
            return medicineRepository.findAllByDeletedIsFalse(pageable);
        }
    }

    // lấy chi tiết 1
    public Optional<Medicine> findById(Long medicineId) {
        Optional<Medicine> medicineOptional = medicineRepository.findById(medicineId);
        if (medicineOptional.isPresent() && medicineOptional.get().getImage() != null) {
            medicineOptional
                .get()
                .setImage(applicationProperties.getImageUrl() + File.separator + medicineOptional.get().getImage() + "?type=medicine");
        }
        return medicineOptional;
    }

    public MedicineDTO findDTOById(Long medicineId) {
        if (medicineId == null) return null;
        MedicineDTO medicineDTO = new MedicineDTO(medicineRepository.findById(medicineId).get());
        if (medicineDTO.getImage() != null) {
            medicineDTO.setImage(applicationProperties.getImageUrl() + File.separator + medicineDTO.getImage() + "?type=medicine");
        }
        return medicineDTO;
    }
}
