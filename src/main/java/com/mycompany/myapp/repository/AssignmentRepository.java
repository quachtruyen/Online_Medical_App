package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Assignment;
import liquibase.pro.packaged.A;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    int countAllByDoctorIdAndPatientStatus(Long id, String status);

    Page<Assignment> findAllByDoctorIdAndPatientStatus(Long id, String status, Pageable pageable);

    Assignment findTopByPatientIdAndPatientStatus(Long patientId, String status);

    Assignment findTopByPatientId(Long patientId);

    Assignment findTopByPatientIdOrderByAssignDateDesc(Long patientId);
}
