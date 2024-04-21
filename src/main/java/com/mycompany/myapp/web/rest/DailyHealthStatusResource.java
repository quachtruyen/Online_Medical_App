package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.config.ApplicationProperties;
import com.mycompany.myapp.domain.DailyHealthStatus;
import com.mycompany.myapp.domain.DoctorAction;
import com.mycompany.myapp.repository.DailyHealthStatusRepositoryCustom;
import com.mycompany.myapp.repository.DoctorActionRepository;
import com.mycompany.myapp.service.DailyHealthStatusService;
import com.mycompany.myapp.service.dto.DailyHealthStatusDTO;
import java.io.File;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-health-status")
public class DailyHealthStatusResource {

    private final DailyHealthStatusService dailyHealthStatusService;

    private final DailyHealthStatusRepositoryCustom dailyHealthStatusRepositoryCustom;

    private final DoctorActionRepository doctorActionRepository;

    private final ApplicationProperties applicationProperties;

    public DailyHealthStatusResource(
        DailyHealthStatusService dailyHealthStatusService,
        DailyHealthStatusRepositoryCustom dailyHealthStatusRepositoryCustom,
        DoctorActionRepository doctorActionRepository,
        ApplicationProperties applicationProperties
    ) {
        this.dailyHealthStatusService = dailyHealthStatusService;
        this.dailyHealthStatusRepositoryCustom = dailyHealthStatusRepositoryCustom;
        this.doctorActionRepository = doctorActionRepository;
        this.applicationProperties = applicationProperties;
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<DailyHealthStatusDTO>> searchAllByPatientId(Pageable pageable, @PathVariable Long patientId) {
        //        Page<DailyHealthStatus> dailyHealthStatusPage = dailyHealthStatusService.search(pageable, patientId);
        //
        //        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
        //            ServletUriComponentsBuilder.fromCurrentRequest(),
        //            dailyHealthStatusPage
        //        );
        Integer total = dailyHealthStatusRepositoryCustom.countAllByPatient(patientId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Total-Count", total.toString());
        List<DailyHealthStatusDTO> list = dailyHealthStatusRepositoryCustom.findAllByPatient(
            patientId,
            pageable.getPageSize(),
            pageable.getOffset()
        );

        list.forEach(
            elm -> {
                DoctorAction doctorAction = doctorActionRepository.findTopByDailyHealthStatusIdOrderByCreatedAtDesc(elm.getId());
                elm.setDoctorActionId(doctorAction != null ? doctorAction.getId() : null);
                if (elm.getImage() != null) {
                    elm.setImage(applicationProperties.getImageUrl() + File.separator + elm.getImage() + "?type=symptom");
                }
            }
        );
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<DailyHealthStatus> create(@RequestBody DailyHealthStatusDTO dailyHealthStatusDTO) {
        return ResponseEntity.ok(dailyHealthStatusService.create(dailyHealthStatusDTO));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<DailyHealthStatusDTO> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(dailyHealthStatusService.getHealthStatusById(id));
    }

    @GetMapping("/check-update-today")
    public boolean checkUserUpdateHealthToday() {
        return dailyHealthStatusService.checkUpdateToday();
    }
}
