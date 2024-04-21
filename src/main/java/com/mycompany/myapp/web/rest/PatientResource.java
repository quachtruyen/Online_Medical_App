package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.dto.UserPatientDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/doctor")
@Slf4j
public class PatientResource {

    private final UserService userService;

    public PatientResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/patients")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.DOCTOR + "\")")
    public ResponseEntity<List<UserPatientDTO>> getAllPatients(
        Pageable pageable,
        @RequestParam String healthStatus,
        @RequestParam String patientStatus
    ) {
        final Page<UserPatientDTO> page = userService.findAllPatients(pageable, healthStatus, patientStatus);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/patients/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    public ResponseEntity<UserPatientDTO> getPatientDetail(@PathVariable Long id) {
        UserPatientDTO userPatientDTO = userService.findOneByPatientId(id);
        if (userPatientDTO == null) {
            throw new RuntimeException("This patient information could not be found");
        }
        return new ResponseEntity<>(userPatientDTO, HttpStatus.OK);
    }
}
