package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Page<Medicine> findAllByNameContainingIgnoreCase(Pageable pageable, String search);

    Page<Medicine> findAllByNameContainingIgnoreCaseAndDeletedIsFalse(Pageable pageable, String search);

    Page<Medicine> findAllByDeletedIsFalse(Pageable pageable);
}
