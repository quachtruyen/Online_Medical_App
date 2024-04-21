package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Medicine;
import com.mycompany.myapp.repository.MedicineRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.MedicineService;
import com.mycompany.myapp.service.dto.MedicineDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.errors.EmailAlreadyUsedException;
import com.mycompany.myapp.web.rest.errors.LoginAlreadyUsedException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/medicine")
public class MedicineResource {

    private final Logger log = LoggerFactory.getLogger(MedicineResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicineService medicineService;

    private final MedicineRepository medicineRepository;

    public MedicineResource(MedicineService medicineService, MedicineRepository medicineRepository) {
        this.medicineService = medicineService;
        this.medicineRepository = medicineRepository;
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Medicine> createMedicine(@Valid @RequestBody MedicineDTO medicineDTO) throws URISyntaxException {
        log.debug("REST request to save Medicine : {}", medicineDTO);

        if (medicineDTO.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", "medicineManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else {
            Medicine newMedicine = medicineService.createOrUpdate(medicineDTO);
            return ResponseEntity
                .created(new URI("/api/admin/medicine/" + newMedicine.getId()))
                .headers(HeaderUtil.createAlert(applicationName, "medicineManagement.created", newMedicine.getId().toString()))
                .body(newMedicine);
        }
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Medicine> updateMedicine(@Valid @RequestBody MedicineDTO medicineDTO) {
        log.debug("REST request to update Medicine : {}", medicineDTO);
        Optional<Medicine> existingUser = medicineRepository.findById(medicineDTO.getId());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(medicineDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = medicineRepository.findById(medicineDTO.getId());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(medicineDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Medicine updatedMedicine = medicineService.createOrUpdate(medicineDTO);

        return ResponseUtil.wrapOrNotFound(
            Optional.of(updatedMedicine),
            HeaderUtil.createAlert(applicationName, "medicineManagement.updated", medicineDTO.getId().toString())
        );
    }

    @GetMapping("/list")
    public ResponseEntity<List<Medicine>> getListMedicineUser(Pageable pageable, @RequestParam String search) {
        final Page<Medicine> page = medicineService.getListMedicine(pageable, search, false);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<Medicine>> getListMedicine(Pageable pageable, @RequestParam String search) {
        final Page<Medicine> page = medicineService.getListMedicine(pageable, search, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Medicine> getMedicine(@PathVariable Long id) {
        log.debug("REST request to get Medicine : {}", id);
        return ResponseUtil.wrapOrNotFound(medicineService.findById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        log.debug("REST request to delete Medicine: {}", id);
        medicineService.deleteMedicine(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(applicationName, "medicineManagement.deleted", id.toString()))
            .build();
    }
}
