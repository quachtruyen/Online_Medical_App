package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Patient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data JPA repository for the {@link Patient} entity.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findOneByUserId(Long id);

    List<Patient> findAllByIdIn(List<Long> ids);

    @Transactional
    void deleteAllByUserId(Long id);
}
