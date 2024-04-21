package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.repository.DailyHealthStatusRepository;
import com.mycompany.myapp.repository.DoctorActionRepository;
import com.mycompany.myapp.repository.DoctorRepository;
import com.mycompany.myapp.repository.PatientRepository;
import com.mycompany.myapp.service.dto.DoctorActionDTO;
import com.mycompany.myapp.service.dto.NotificationDTO;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorActionService {

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final DoctorActionRepository doctorActionRepository;

    private final DailyHealthStatusRepository dailyHealthStatusRepository;

    private final NotificationService notificationService;

    private final UserService userService;

    public DoctorActionService(
        DoctorActionRepository doctorActionRepository,
        DoctorRepository doctorRepository,
        PatientRepository patientRepository,
        DailyHealthStatusRepository dailyHealthStatusRepository,
        NotificationService notificationService,
        UserService userService
    ) {
        this.doctorRepository = doctorRepository;
        this.doctorActionRepository = doctorActionRepository;
        this.patientRepository = patientRepository;
        this.dailyHealthStatusRepository = dailyHealthStatusRepository;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    public void create(DoctorActionDTO doctorActionDTO) {
        DoctorAction doctorAction = new DoctorAction();
        doctorAction.setCreatedAt(Instant.now());
        doctorAction.setDoctorId(doctorActionDTO.getDoctorId());
        doctorAction.setAdvise(doctorActionDTO.getAdvise());
        doctorAction.setUpdatedAt(Instant.now());
        doctorAction.setNote(doctorActionDTO.getNote());
        doctorAction.setPrescriptionId(doctorActionDTO.getPrescriptionId());
        doctorAction.setDailyHealthStatusId(doctorActionDTO.getDailyHealthStatusId());
        doctorActionRepository.save(doctorAction);

        Optional<DailyHealthStatus> dailyHealthStatus = dailyHealthStatusRepository.findById(doctorActionDTO.getDailyHealthStatusId());
        if (dailyHealthStatus.isEmpty()) {
            throw new RuntimeException("không tìm thấy daily health status");
        }
        Optional<Patient> patient = patientRepository.findById(dailyHealthStatus.get().getPatientId());
        if (patient.isEmpty()) {
            throw new RuntimeException("Không tìm thấy patient");
        }
        Optional<Doctor> doctor = doctorRepository.findById(doctorActionDTO.getDoctorId());
        if (doctor.isEmpty()) {
            throw new RuntimeException("Không tìm thấy bác sĩ");
        }
        User patientUser = userService.findByUserId(patient.get().getUserId());
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setContent(
            "Sick state of the day " + dailyHealthStatus.get().getCreatedAt().toString() + " yours has been treated by a doctor "
        );
        notificationDTO.setCreatedAt(Instant.now());
        notificationDTO.setTitle("Doctor notification");
        notificationDTO.setUserId(doctor.get().getId());
        notificationDTO.setUrl("/daily-health-status/" + dailyHealthStatus.get().getId() + "/view");
        Long notificationId = notificationService.createNotification(notificationDTO);
        notificationService.createUserNotification(notificationId, patientUser.getId());
    }

    public Page<DoctorAction> search(Pageable pageable, Long healthStatusId) {
        return doctorActionRepository.findAllByDailyHealthStatusId(pageable, healthStatusId);
    }

    public DoctorAction findByDailyHealthStatusId(Long healthStatusId) {
        return doctorActionRepository.findTopByDailyHealthStatusIdOrderByCreatedAtDesc(healthStatusId);
    }

    public DoctorAction findById(Long id) {
        return doctorActionRepository.findById(id).orElse(null);
    }
}
