package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Doctor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Doctor} entity.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findOneByUserId(Long id);

    void deleteAllByUserId(Long id);
}
