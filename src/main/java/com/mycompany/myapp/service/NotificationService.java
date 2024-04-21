package com.mycompany.myapp.service;

import com.mycompany.myapp.constant.AssignmentStatus;
import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.repository.*;
import com.mycompany.myapp.service.dto.NotificationDTO;
import com.mycompany.myapp.service.dto.UserNotificationDTO;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.print.Doc;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final UserNotificationCustom userNotificationCustom;

    private final UserNotificationRepository userNotificationRepository;

    private final SimpMessageSendingOperations messagingTemplate;

    private final UserService userService;

    private final AssignmentRepository assignmentRepository;

    public NotificationService(
        NotificationRepository notificationRepository,
        DoctorRepository doctorRepository,
        PatientRepository patientRepository,
        UserNotificationCustom userNotificationCustom,
        UserNotificationRepository userNotificationRepository,
        SimpMessageSendingOperations messagingTemplate,
        UserService userService,
        AssignmentRepository assignmentRepository
    ) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.userNotificationCustom = userNotificationCustom;
        this.userNotificationRepository = userNotificationRepository;
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.assignmentRepository = assignmentRepository;
    }

    public Long createNotification(NotificationDTO notificationDto) {
        Notification notification = new Notification();
        notification.setContent(notificationDto.getContent());
        notification.setCreatedAt(Instant.now());
        notification.setUrl(notificationDto.getUrl());
        notification.setTitle(notificationDto.getTitle());
        notification.setFromUserId(notificationDto.getUserId()); // Ai tạo

        notification = notificationRepository.save(notification);
        return notification.getId();
    }

    public void createNotificationForAssignment(Assignment assignment) {
        NotificationDTO notificationAssignment = new NotificationDTO();
        Optional<Patient> patient = patientRepository.findById(assignment.getPatientId());
        Optional<Doctor> doctor = doctorRepository.findById(assignment.getDoctorId());
        Optional<User> user = userService.getUserWithAuthorities();
        User patientDoctor = userService.findByUserId(doctor.get().getUserId());
        User patientPatient = userService.findByUserId(patient.get().getUserId());

        notificationAssignment.setContent(
            "You have been assigned to care for the patient " +
            patientPatient.getId() +
            " " +
            patientPatient.getFirstName() +
            " " +
            patientPatient.getLastName()
        );
        notificationAssignment.setCreatedAt(Instant.now());
        notificationAssignment.setTitle("Designation Notice");
        notificationAssignment.setUserId(user.get().getId());
        notificationAssignment.setUrl("/doctor/patient-management/" + patient.get().getId() + "/view");
        Long notificationId = this.createNotification(notificationAssignment);
        this.createUserNotification(notificationId, patientDoctor.getId());

        notificationAssignment.setContent(
            "You have been assigned to be under the care of a doctor " +
            patientDoctor.getId() +
            " " +
            patientDoctor.getFirstName() +
            " " +
            patientDoctor.getLastName()
        );
        notificationAssignment.setUrl("/account/settings");
        Long notificationIdPatient = this.createNotification(notificationAssignment);
        this.createUserNotification(notificationIdPatient, patientPatient.getId());
    }

    public void createNotificationForDeleteAssignment(Assignment assignment) {
        NotificationDTO notificationAssignment = new NotificationDTO();
        Optional<Patient> patient = patientRepository.findById(assignment.getPatientId());
        Optional<Doctor> doctor = doctorRepository.findById(assignment.getDoctorId());
        Optional<User> user = userService.getUserWithAuthorities();
        User patientDoctor = userService.findByUserId(doctor.get().getUserId());
        User patientPatient = userService.findByUserId(patient.get().getUserId());

        notificationAssignment.setContent(
            "You have been cleared of a care assignment for the patient " +
            patientPatient.getId() +
            " " +
            patientPatient.getFirstName() +
            " " +
            patientPatient.getLastName()
        );
        notificationAssignment.setCreatedAt(Instant.now());
        notificationAssignment.setTitle("Indication deletion notice");
        notificationAssignment.setUserId(user.get().getId());

        Long notificationId = this.createNotification(notificationAssignment);
        this.createUserNotification(notificationId, patientDoctor.getId());

        notificationAssignment.setContent(
            "You have been cleared of the order to be cared for by a doctor " +
            patientDoctor.getId() +
            " " +
            patientDoctor.getFirstName() +
            " " +
            patientDoctor.getLastName()
        );

        Long notificationIdPatient = this.createNotification(notificationAssignment);
        this.createUserNotification(notificationIdPatient, patientPatient.getId());
    }

    public void createNotificationForDailyHealthStatus(DailyHealthStatus dailyHealthStatus) {
        NotificationDTO notificationDailyHealthStatus = new NotificationDTO();
        notificationDailyHealthStatus.setCreatedAt(Instant.now());
        notificationDailyHealthStatus.setTitle("Notify patients about health updates ");
        Optional<Patient> patient = patientRepository.findById(dailyHealthStatus.getPatientId());
        User patientUser = userService.findByUserId(patient.get().getUserId());
        Assignment assignment = assignmentRepository.findTopByPatientIdAndPatientStatus(
            dailyHealthStatus.getPatientId(),
            AssignmentStatus.ACCEPT
        );

        if (assignment == null) {
            // Gửi cho toàn bộ admin nếu patient chưa được assign cho bác sĩ nào
            notificationDailyHealthStatus.setContent(
                "Patient " +
                patientUser.getId() +
                " " +
                patientUser.getFirstName() +
                " " +
                patientUser.getLastName() +
                " Just updated health status. You need to appoint a doctor to take care of you right away!"
            );
            notificationDailyHealthStatus.setUrl("/admin/patient-management");
            Long notificationId = this.createNotification(notificationDailyHealthStatus);
            List<User> users = userService.findAllAdmin();
            for (User user : users) {
                this.createUserNotification(notificationId, user.getId());
            }
        } else {
            // Gửi cho bác sĩ
            Optional<Doctor> doctor = doctorRepository.findById(assignment.getDoctorId());
            User patientDoctor = userService.findByUserId(doctor.get().getUserId());
            notificationDailyHealthStatus.setContent(
                "Patient " +
                patientUser.getId() +
                " " +
                patientUser.getFirstName() +
                " " +
                patientUser.getLastName() +
                " just updated health status"
            );
            notificationDailyHealthStatus.setUrl("/doctor/patient-management/" + patient.get().getId() + "/view");
            Long notificationId = this.createNotification(notificationDailyHealthStatus);
            this.createUserNotification(notificationId, patientDoctor.getId());
        }
    }

    public void createUserNotification(Long notificationId, Long toUserId) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (notification.isEmpty()) {
            throw new RuntimeException("This notification does not exist");
        }
        UserNotification userNotification = new UserNotification();
        userNotification.setCreatedAt(Instant.now());
        userNotification.setNotificationId(notificationId);
        userNotification.setUserId(toUserId);
        userNotification.setStatus("unread");
        userNotificationRepository.save(userNotification);

        User user = userService.findByUserId(toUserId);

        if (user != null) {
            NotificationDTO notificationDTO = new NotificationDTO(notification.get());
            notificationDTO.setCreatedAt(userNotification.getCreatedAt());
            notificationDTO.setStatus(userNotification.getStatus());
            notificationDTO.setUrl(notification.get().getUrl());
            messagingTemplate.convertAndSendToUser(user.getLogin(), "notification", notificationDTO); // gửi thông tin Notification đến userlogin đó
        } else {
            throw new RuntimeException("Recipient does not exist");
        }
    }

    public List<NotificationDTO> getListNotification(Pageable pageable, String search, String status) { // pageable hỗ trợ phân trang
        Long userId = userService.getUserWithAuthorities().get().getId();
        return userNotificationCustom.findAllByFilter(userId, search, status, pageable.getPageSize(), pageable.getOffset());
    }

    public int countAllNotification(String search, String status) {
        Long userId = userService.getUserWithAuthorities().get().getId();
        return userNotificationCustom.countAllByFilter(userId, search, status);
    }

    public void statusNotification(Long notificationId, UserNotificationDTO userNotificationDto) {
        Optional<UserNotification> userNotificationOptional = userNotificationRepository.findById(userNotificationDto.getId());
        if (userNotificationOptional.isEmpty()) {
            throw new RuntimeException("This message is not available");
        }

        UserNotification userNotification = userNotificationOptional.get();
        userNotification.setStatus("read");
        userNotificationRepository.save(userNotification);
    }

    public void readAllNotifications() {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            Long userId = user.get().getId();
            List<UserNotification> userNotifications = userNotificationRepository.findAllByUserIdAndStatus(userId, "unread");
            userNotifications.forEach(elm -> elm.setStatus("read"));
            userNotificationRepository.saveAll(userNotifications);
        } else {
            throw new RuntimeException("Can't get user information");
        }
    }

    public void readOne(Long notificationId) {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            Long userId = user.get().getId();
            UserNotification userNotification = userNotificationRepository.findOneByUserIdAndNotificationId(userId, notificationId);
            if (userNotification == null) {
                throw new RuntimeException("Can't get notification information");
            }
            userNotification.setStatus("read");
            userNotificationRepository.save(userNotification);
        } else {
            throw new RuntimeException("Can't get user information");
        }
    }

    public Integer countTotalUnreadByCurrentUser() {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            Long userId = user.get().getId();
            return userNotificationRepository.countAllByUserIdAndStatus(userId, "unread");
        }
        throw new RuntimeException("Can't get user information");
    }

    // lay chi tiet 1 notification
    public Optional<Notification> findById(Long notificationId) {
        return notificationRepository.findById(notificationId);
    }

    public void createNotificationForFinishAssignment(Assignment assignment) {
        NotificationDTO notificationAssignment = new NotificationDTO();
        Optional<Patient> patient = patientRepository.findById(assignment.getPatientId());
        Optional<Doctor> doctor = doctorRepository.findById(assignment.getDoctorId());
        Optional<User> user = userService.getUserWithAuthorities();
        User patientDoctor = userService.findByUserId(doctor.get().getUserId());
        User patientPatient = userService.findByUserId(patient.get().getUserId());

        notificationAssignment.setContent(
            "You have been updated as cured by the doctor " + patientDoctor.getFirstName() + " " + patientDoctor.getLastName()
        );
        notificationAssignment.setCreatedAt(Instant.now());
        notificationAssignment.setTitle("Notify patient recovery");
        notificationAssignment.setUserId(user.get().getId());

        Long notificationId = this.createNotification(notificationAssignment);
        this.createUserNotification(notificationId, patientPatient.getId()); // Gửi cho bệnh nhân

        notificationAssignment.setContent(
            "Patient " +
            patientPatient.getFirstName() +
            " " +
            patientPatient.getLastName() +
            " has been updated by the doctor " +
            patientDoctor.getFirstName() +
            " " +
            patientDoctor.getLastName()
        );

        notificationAssignment.setUrl("/doctor/patient-management/" + patient.get().getId() + "/view");

        Long notificationIdAdmin = this.createNotification(notificationAssignment);

        List<User> users = userService.findAllAdmin();
        for (User userAdmin : users) {
            this.createUserNotification(notificationIdAdmin, userAdmin.getId()); // Gửi cho admin
        }
    }
}
