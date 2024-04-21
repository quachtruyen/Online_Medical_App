package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Doctor;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.dto.UserInfoDTO;
import com.mycompany.myapp.service.patientdto.SearchDoctorResponse;
import java.util.*;
import java.util.Collections;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/doctor")
public class DoctorResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList(
            "id",
            "login",
            "firstName",
            "lastName",
            "email",
            "activated",
            "langKey",
            "createdBy",
            "createdDate",
            "lastModifiedBy",
            "lastModifiedDate"
        )
    );

    // private final Logger log = LoggerFactory.getLogger(DoctorResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final UserService userService;

    private final UserRepository userRepository;

    private final MailService mailService;

    public DoctorResource(UserService userService, UserRepository userRepository, MailService mailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @GetMapping("/detail/assignedToMe")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PATIENT + "\")")
    public ResponseEntity<UserInfoDTO> getDoctorCareCurrentUser() {
        UserInfoDTO doctor = userService.getDoctorCareCurrentUser();
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SearchDoctorResponse>> getListDoctor(Pageable pageable, @RequestParam String search) {
        List<SearchDoctorResponse> doctorResponses = userService.getAllDoctor(pageable, search);
        Integer total = userService.countAllDoctor(search);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Total-Count", total.toString());
        return new ResponseEntity<>(doctorResponses, headers, HttpStatus.OK);
    }
}
