package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MedicinePrescription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicinePrescriptionRepository extends JpaRepository<MedicinePrescription, Long> {
    List<MedicinePrescription> findAllByPrescriptionId(Long prescriptionId);
    void deleteAllByIdIn(List<Long> ids);
}
