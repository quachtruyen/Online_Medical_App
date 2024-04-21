package com.mycompany.myapp.service;

import com.mycompany.myapp.config.ApplicationProperties;
import com.mycompany.myapp.domain.DailyHealthStatus;
import com.mycompany.myapp.domain.Patient;
import com.mycompany.myapp.repository.DailyHealthStatusRepository;
import com.mycompany.myapp.service.dto.DailyHealthStatusDTO;
import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DailyHealthStatusService {

    private final DailyHealthStatusRepository dailyHealthStatusRepository;

    private final ApplicationProperties applicationProperties;

    private final DoctorActionService doctorActionService;

    private final NotificationService notificationService;

    private final UserService userService;

    public DailyHealthStatusService(
        DailyHealthStatusRepository dailyHealthStatusRepository,
        ApplicationProperties applicationProperties,
        DoctorActionService doctorActionService,
        NotificationService notificationService,
        UserService userService
    ) {
        this.dailyHealthStatusRepository = dailyHealthStatusRepository;
        this.applicationProperties = applicationProperties;
        this.doctorActionService = doctorActionService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    // Cập nhật trạng thái sức khỏe patient hàng ngày
    public DailyHealthStatus create(DailyHealthStatusDTO dailyHealthStatusDTO) {
        Patient patient = userService.getCurrentUserIfIsPatient();
        DailyHealthStatus dailyHealthStatus;

        if (dailyHealthStatusDTO.getId() != null) {
            Optional<DailyHealthStatus> dailyHealthStatusOptional = dailyHealthStatusRepository.findById(dailyHealthStatusDTO.getId());
            if (dailyHealthStatusOptional.isEmpty()) {
                throw new RuntimeException("This state does not exist");
            }
            dailyHealthStatus = dailyHealthStatusOptional.get();
            dailyHealthStatus.setUpdatedAt(Instant.now());
        } else { // tạo mới nếu chưa tồn tại
            dailyHealthStatus = new DailyHealthStatus();
        }
        dailyHealthStatus.setPatientId(patient.getId());
        dailyHealthStatus.setDate(dailyHealthStatusDTO.getDate());
        dailyHealthStatus.setHealthStatus(dailyHealthStatusDTO.getHealthStatus());
        dailyHealthStatus.setNotes(dailyHealthStatusDTO.getNotes());
        dailyHealthStatus.setSymptoms(dailyHealthStatusDTO.getSymptoms());
        dailyHealthStatus.setCreatedAt(Instant.now());

        dailyHealthStatus = dailyHealthStatusRepository.save(dailyHealthStatus);

        notificationService.createNotificationForDailyHealthStatus(dailyHealthStatus); // gui tin nhan khi patient update daily healh status

        return dailyHealthStatus;
    }

    // láy ra danh sách health status của patient
    public List<DailyHealthStatus> getListStatus(Long patientId) {
        List<DailyHealthStatus> result = dailyHealthStatusRepository.findAllByPatientIdOrderByDateDesc(patientId);
        return result;
    }

    public DailyHealthStatus getHealthStatusByDateAndPatientId(Instant date, Long patientId) {
        Instant from = date.truncatedTo(ChronoUnit.DAYS);
        Instant to = date.truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS);
        DailyHealthStatus dailyHealthStatus = dailyHealthStatusRepository.findTopByDateAfterAndDateBeforeAndPatientId(from, to, patientId);
        if (dailyHealthStatus == null) {
            throw new RuntimeException("Không có dữ liệu bệnh nhân cập nhật trong ngày này");
        }
        return dailyHealthStatus;
    }

    // Lấy chi tiết health status bằng id
    public DailyHealthStatusDTO getHealthStatusById(Long dailyHealthStatusId) {
        Optional<DailyHealthStatus> dailyHealthStatus = dailyHealthStatusRepository.findById(dailyHealthStatusId);
        if (dailyHealthStatus.isEmpty()) {
            throw new RuntimeException("Không có dữ liệu bệnh nhân cập nhật bằng id này");
        }
        if (dailyHealthStatus.get().getImage() != null) {
            dailyHealthStatus
                .get()
                .setImage(applicationProperties.getImageUrl() + File.separator + dailyHealthStatus.get().getImage() + "?type=symptom");
        }
        return new DailyHealthStatusDTO(dailyHealthStatus.get());
    }

    public Page<DailyHealthStatus> search(Pageable pageable, Long patientId) {
        return dailyHealthStatusRepository.findAllByPatientIdOrderByDateDesc(pageable, patientId);
    }

    public boolean checkUpdateToday() {
        Patient patient = userService.getCurrentUserIfIsPatient(); // lay thong tin Patient neu User hien tai gui Request den API nay voi role là Patient
        Instant now = Instant.now();
        Instant from = now.truncatedTo(ChronoUnit.DAYS); // lay thoi gian dau ngay
        Instant to = now.truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS); // lay thoi gian cuoi ngay
        DailyHealthStatus dailyHealthStatus = dailyHealthStatusRepository.findTopByDateAfterAndDateBeforeAndPatientId(
            from,
            to,
            patient.getId() // check xem trong ngay hom day co ban ghi nao chua
        );
        return dailyHealthStatus != null;
    }

    public void createOrUpdateImageSymptom(Long id, String fileName) {
        DailyHealthStatus dailyHealthStatus;
        Optional<DailyHealthStatus> dailyHealthStatusOptional = dailyHealthStatusRepository.findById(id);
        if (dailyHealthStatusOptional.isEmpty()) {
            throw new RuntimeException("Không tồn tại trạng thái này");
        }
        dailyHealthStatus = dailyHealthStatusOptional.get();
        if (StringUtils.isNotBlank(fileName)) {
            dailyHealthStatus.setImage(fileName);
            dailyHealthStatusRepository.save(dailyHealthStatus);
        }
    }
}
