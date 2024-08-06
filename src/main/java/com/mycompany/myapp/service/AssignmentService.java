package com.mycompany.myapp.service;

import com.mycompany.myapp.constant.AssignmentStatus;
import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.repository.*;
import com.mycompany.myapp.service.dto.AssignmentDTO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AssignmentRepository assigmentRepository;
    private final GroupUserRepository groupUserRepository;
    private final GroupChatRepository groupChatRepository;
    private final NotificationService notificationService;

    public AssignmentService(
        PatientRepository patientRepository,
        DoctorRepository doctorRepository,
        AssignmentRepository assigmentRepository,
        GroupUserRepository groupUserRepository, GroupChatRepository groupChatRepository, NotificationService notificationService
    ) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.assigmentRepository = assigmentRepository;
        this.groupUserRepository = groupUserRepository;
        this.groupChatRepository = groupChatRepository;
        this.notificationService = notificationService;
    }

    public void assign(AssignmentDTO assignmentDTO) { // assign patient to doctor
        // tìm patient và đang bị bệnh
        Optional<Patient> patientOptional = patientRepository.findById(assignmentDTO.getPatientId());
        if (patientOptional.isEmpty()) {
            throw new RuntimeException("Không tồn tại bênh nhân này");
        }

        Patient patient = patientOptional.get();

        // tìm doctor và ktra doctor đang care <= 20 bệnh nhân
        Optional<Doctor> doctorOptional = doctorRepository.findById(assignmentDTO.getDoctorId());
        if (doctorOptional.isEmpty()) {
            throw new RuntimeException("Không tồn tại bác sĩ này");
        }
        Doctor doctor = doctorOptional.get();

        int totalAssiged = assigmentRepository.countAllByDoctorIdAndPatientStatus(doctor.getId(), "sick");
        if (totalAssiged >= 20) {
            throw new RuntimeException("Đang vượt quá số lượng bệnh nhân cho phép");
        }

        Assignment assignment = new Assignment();
        assignment.setDoctorId(doctor.getId());
        assignment.setPatientId(patient.getId());
        assignment.setAssignDate(Instant.now());
        assignment.setPatientStatus(AssignmentStatus.ACCEPT);

        assignment = assigmentRepository.save(assignment);

        GroupChat groupChat = new GroupChat();
//        groupChat.setNameGroup();

        groupChatRepository.save(groupChat);

        GroupUser groupUser = new GroupUser();
        groupUser.setIdUser(doctor.getId());
        groupUser.setIdGroup(patient.getId());
        groupUser.setCreateDate(Instant.now());

        groupUser = groupUserRepository.save(groupUser);

        notificationService.createNotificationForAssignment(assignment);
    }

    public void deleteAssignment(Long assignmentId) {
        Optional<Assignment> assignment = assigmentRepository.findById(assignmentId);
        if (assignment.isEmpty()) {
            throw new RuntimeException("Không tồn tại assigment này");
        }
        notificationService.createNotificationForDeleteAssignment(assignment.get());
        assigmentRepository.deleteById(assignmentId);
    }

    public List<Patient> getListPatientOfDoctor(String status, Long doctorId, Pageable pageable) {
        Page<Assignment> assignment = assigmentRepository.findAllByDoctorIdAndPatientStatus(doctorId, status, pageable);
        List<Long> patientIds = assignment.stream().map(Assignment::getPatientId).collect(Collectors.toList());
        List<Patient> patients = patientRepository.findAllByIdIn(patientIds);
        return patients;
    }

    // lấy chi tiết 1 assignment
    public Optional<Assignment> findById(Long assignmentId) {
        return assigmentRepository.findById(assignmentId);
    }

    public void assign(Long assignmentId) { // assign patient to doctor
        // tìm patient và đang bị bệnh
        Optional<Assignment> assignmentOptional = assigmentRepository.findById(assignmentId);
        if (assignmentOptional.isEmpty()) {
            throw new RuntimeException("Không tồn tại chỉ định này");
        }
        assignmentOptional.get().setPatientStatus(AssignmentStatus.FINISH);
        assigmentRepository.save(assignmentOptional.get());
        notificationService.createNotificationForFinishAssignment(assignmentOptional.get());
    }
}
