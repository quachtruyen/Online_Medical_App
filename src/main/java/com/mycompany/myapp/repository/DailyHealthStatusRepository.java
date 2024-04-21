package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DailyHealthStatus;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyHealthStatusRepository extends JpaRepository<DailyHealthStatus, Long> {
    List<DailyHealthStatus> findAllByPatientIdOrderByDateDesc(Long patientId);

    Page<DailyHealthStatus> findAllByPatientIdOrderByDateDesc(Pageable pageable, Long patientId);

    DailyHealthStatus findTopByDateAfterAndDateBeforeAndPatientId(Instant from, Instant to, Long patientId);
}
