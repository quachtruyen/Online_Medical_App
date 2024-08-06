package com.mycompany.myapp.service;

import com.mycompany.myapp.config.ApplicationProperties;
import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.constant.AssignmentStatus;
import com.mycompany.myapp.constant.DailyHealthStatus;
import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.repository.*;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.PatientDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import com.mycompany.myapp.service.dto.UserInfoDTO;
import com.mycompany.myapp.service.dto.UserPatientDTO;
import com.mycompany.myapp.service.patientdto.SearchDoctorResponse;
import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> findAllAdmin() {
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        return userRepository.findAllByAuthoritiesContains(authority);
    }

    public void createOrUpdateImageCertificate(Long id, String fileName) {
        Patient patient;
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            throw new RuntimeException("Không tồn tại bệnh nhân này");
        }
        patient = patientOptional.get();
        if (StringUtils.isNotBlank(fileName)) {
            patient.setImageCertificate(fileName);
            patientRepository.save(patient);
        }
    }

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final DoctorRepository doctorRepository;

    private final ApplicationProperties applicationProperties;

    private final PatientRepository patientRepository;

    private final AssignmentRepository assignmentRepository;

    private final AuthorityRepository authorityRepository;

    private final UserRepositoryCustom userRepositoryCustom;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        DoctorRepository doctorRepository,
        ApplicationProperties applicationProperties,
        PatientRepository patientRepository,
        AssignmentRepository assignmentRepository,
        UserRepositoryCustom userRepositoryCustom
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.doctorRepository = doctorRepository;
        this.applicationProperties = applicationProperties;
        this.patientRepository = patientRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepositoryCustom = userRepositoryCustom;
    }

    public Page<UserPatientDTO> findAllPatients(Pageable pageable, String healthStatus, String patientStatus) {
        Doctor doctor = getCurrentUserIfIsDoctor();
        List<UserPatientDTO> users = userRepositoryCustom.findAllPatients(
            doctor.getId(),
            healthStatus,
            patientStatus,
            pageable.getPageSize(),
            pageable.getOffset()
        );
        int total = userRepositoryCustom.countAllPatients(doctor.getId(), healthStatus, patientStatus);
        return new PageImpl<>(users, pageable, total);
    }

    public UserPatientDTO findOneByPatientId(Long id) {
        UserPatientDTO userPatientDTO = userRepositoryCustom.findOneByPatientId(id);
        if (userPatientDTO != null) {
            Assignment assignment = assignmentRepository.findTopByPatientIdOrderByAssignDateDesc(userPatientDTO.getId());
            userPatientDTO.setAssignment(assignment);
            userPatientDTO.setImageCertificate(
                applicationProperties.getImageUrl() + File.separator + userPatientDTO.getImageCertificate() + "?type=certificate"
            );
            if (assignment != null) {
                Optional<Doctor> doctor = doctorRepository.findById(assignment.getDoctorId());
                doctor.ifPresent(userPatientDTO::setDoctor);
            }
        }
        return userPatientDTO;
    }

    public UserInfoDTO getDoctorCareCurrentUser() {
        Optional<User> user = getUserWithAuthorities();
        if (user.isPresent()) {
            Optional<Patient> patient = patientRepository.findOneByUserId(user.get().getId());
            if (patient.isPresent()) {
                Assignment assignment = assignmentRepository.findTopByPatientIdAndPatientStatus(
                    patient.get().getId(),
                    AssignmentStatus.ACCEPT
                );
                if (assignment != null) {
                    Optional<Doctor> doctor = doctorRepository.findById(assignment.getDoctorId());
                    if (doctor.isPresent()) {
                        Optional<User> userDoctor = userRepository.findById(doctor.get().getUserId());
                        if (userDoctor.isPresent()) {
                            return new UserInfoDTO(userDoctor.get());
                        }
                    }
                }
            }
        }
        return null;
    }

    public Doctor getCurrentUserIfIsDoctor() {
        Optional<User> user = getUserWithAuthorities();
        if (user.isPresent()) {
            Optional<Doctor> doctor = doctorRepository.findOneByUserId(user.get().getId());
            if (doctor.isPresent()) {
                return doctor.get();
            }
            throw new RuntimeException("User hiện tại không phải là bác sĩ");
        }
        throw new RuntimeException("Không tìm thấy user hiện tại");
    }

    public Patient getCurrentUserIfIsPatient() {
        Optional<User> user = getUserWithAuthorities();
        if (user.isPresent()) {
            Optional<Patient> patient = patientRepository.findOneByUserId(user.get().getId());
            if (patient.isPresent()) {
                return patient.get();
            }
            throw new RuntimeException("User hiện tại không phải là bệnh nhân");
        }
        throw new RuntimeException("Không tìm thấy user hiện tại");
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository
            .findOneByActivationKey(key)
            .map(
                user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    Patient patient = new Patient();
                    patient.setUserId(user.getId());

                    patientRepository.save(patient);
                    log.debug("Activated user: {}", user);
                    return user;
                }
            );
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(
                user -> {
                    user.setActivated(true);
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    Patient patient = new Patient();
                    patient.setUserId(user.getId());
                    patientRepository.save(patient);
                    return user;
                }
            );
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(User::isActivated)
            .map(
                user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    return user;
                }
            );
    }

    public User registerUser(UserInfoDTO userDTO, String password) {
        userRepository // đại diện cho kết nối đến bảng user trong database
            .findOneByLogin(userDTO.getLogin().toLowerCase()) // tìm tài khoản mình login đã điền vào
            .ifPresent(
                existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new UsernameAlreadyUsedException();
                    }
                }
            );
        userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail()) // tìm xem email đó đã đăng ký tài khoản nào chưa
            .ifPresent( // nếu email đó đã tồn tại thì sẽ sử dụng câu này
                existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new EmailAlreadyUsedException();
                    }
                }
            );
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password); // Encoder dùng để mã hóa password
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not activet
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.PATIENT).ifPresent(authorities::add); // Mặc định đăng ký trên
        // web của mình là một
        // đối tượng nào đó
        // (PATIENT)
        newUser.setAuthorities(authorities);
        userRepository.save(newUser); // Lưu user vào cơ sở dữ liệu
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    public User createUser(UserInfoDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(false);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO
                .getAuthorities()
                .stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<UserInfoDTO> updateUser(UserInfoDTO userDTO) {
        // Optional<User> userOPT = userRepository.findById(userDTO.getId();
//        if (userOPT.isPresent()) {
//            User user = userOPT.get();
//
//        }
        return Optional
            .of(userRepository.findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(
                user -> {
                    Set<String> auth = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
                    if (auth.contains("ROLE_DOCTOR")) {
                        Optional<Doctor> doctorOpt = doctorRepository.findOneByUserId(user.getId());
                        if (doctorOpt.isPresent()) {
                            Doctor doctor = doctorOpt.get();
                            doctor.setPosition(userDTO.getPosition());
                            doctor.setHospital(userDTO.getHospital());
                            doctor.setSpecialize(userDTO.getSpecialize());
                            doctorRepository.save(doctor);
                        } else {
                            Doctor doctor = new Doctor();
                            doctor.setUserId(user.getId());
                            doctor.setPosition(userDTO.getPosition());
                            doctor.setHospital(userDTO.getHospital());
                            doctor.setSpecialize(userDTO.getSpecialize());
                            doctorRepository.save(doctor);
                        }
                    }
                    if (auth.contains("ROLE_PATIENT")) {
                        Optional<Patient> patientOpt = patientRepository.findOneByUserId(user.getId());
                        if (patientOpt.isPresent()) {
                            Patient patient = patientOpt.get();
                            patient.setAddress(userDTO.getAddress());
                            patientRepository.save(patient);
                        } else {
                            Patient patient = new Patient();
                            patient.setUserId(user.getId());
                            patient.setAddress(userDTO.getAddress());
                            patientRepository.save(patient);
                        }
                    }
                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    if (userDTO.getEmail() != null) {
                        user.setEmail(userDTO.getEmail().toLowerCase());
                    }
                    user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(userDTO.isActivated());
                    user.setLangKey(userDTO.getLangKey());

                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO
                        .getAuthorities()
                        .stream()
                        .map(authorityRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(managedAuthorities::add);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                }
            )
            .map(UserInfoDTO::new);
    }

    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login)
            .ifPresent(
                user -> {
                    patientRepository.deleteAllByUserId(user.getId());
                    doctorRepository.deleteAllByUserId(user.getId());
                    userRepository.delete(user);
                    log.debug("Deleted User: {}", user);
                }
            );
    }

    /**
     * Update basic information (first name, last name, email, language) for the
     * current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(
        String firstName,
        String lastName,
        String email,
        String langKey,
        String imageUrl,
        String position,
        String hospital,
        String specialize,
        String phoneNumber
    ) {
        // optional là có hoặc không
        Optional<User> userOpt = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setFirstName(firstName);
            if (email != null) {
                user.setEmail(email.toLowerCase());
            }
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            user.setPhoneNumber(phoneNumber);
            userRepository.save(user);

            Optional<Doctor> doctorOpt = doctorRepository.findOneByUserId(user.getId());
            if (doctorOpt.isPresent()) {
                Doctor doctor = doctorOpt.get();
                doctor.setPosition(position);
                doctor.setHospital(hospital);
                doctor.setSpecialize(specialize);
                doctorRepository.save(doctor);
            } else {
                Doctor doctor = new Doctor();
                doctor.setUserId(user.getId());
                doctor.setPosition(position);
                doctor.setHospital(hospital);
                doctor.setSpecialize(specialize);
                doctorRepository.save(doctor);
            }
            log.debug("Changed Information for User: {}", user);
        }
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(
                user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    log.debug("Changed password for User: {}", user);
                }
            );
    }

    @Transactional(readOnly = true)
    public Page<UserInfoDTO> getAllManagedUsers(Pageable pageable) {
        Page<UserInfoDTO> resutl = userRepository.findAll(pageable).map(UserInfoDTO::new);
        resutl.forEach(
            elm -> {
                Optional<Patient> patient = patientRepository.findOneByUserId(elm.getId());
                if (patient.isPresent()) {
                    Assignment assignment = assignmentRepository.findTopByPatientIdAndPatientStatus(
                        patient.get().getId(),
                        AssignmentStatus.ACCEPT
                    );
                    elm.setAssignmentId(assignment != null ? assignment.getId() : null);
                    elm.setPatient(patient.get());
                }
            }
        );
        return resutl;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserInfoDTO getUserWithAuthoritiesByLogin(String login) {
        Optional<User> userOptional = userRepository.findOneWithAuthoritiesByLogin(login);
        UserInfoDTO userInfoDTO = null;

        if (userOptional.isPresent()) {
            userInfoDTO = new UserInfoDTO(userOptional.get());
            Optional<Doctor> doctor = doctorRepository.findOneByUserId(userOptional.get().getId());
            if (doctor.isPresent()) {
                userInfoDTO.setDoctor(doctor.get());
            }
            Optional<Patient> patient = patientRepository.findOneByUserId(userOptional.get().getId());
            if (patient.isPresent()) {
                Assignment assignment = assignmentRepository.findTopByPatientId(patient.get().getId());
                userInfoDTO.setAssignment(assignment);
                if (assignment != null) {
                    Optional<Doctor> doctor1 = doctorRepository.findById(assignment.getDoctorId());
                    doctor1.ifPresent(userInfoDTO::setDoctor);
                }
                userInfoDTO.setPatient(patient.get());
            }
        }
        return userInfoDTO;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    public UserInfoDTO getUserWithInfo() {
        Optional<User> userOpt = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
        if (userOpt.isPresent()) {
            UserInfoDTO userDTO = new UserInfoDTO(userOpt.get()); // convert kiểu User thành đối tượng DTO để trả về
            // FE
            Optional<Doctor> doctorOpt = doctorRepository.findOneByUserId(userOpt.get().getId());
            doctorOpt.ifPresent(userDTO::setDoctor);
            Patient patient = null;
            Optional<Patient> patientOptional = patientRepository.findOneByUserId(userOpt.get().getId());
            if (patientOptional.isPresent()) {
                patient = new Patient(patientOptional.get());
                if (patient.getImageCertificate() != null) {
                    patient.setImageCertificate(
                        applicationProperties.getImageUrl() + File.separator + patient.getImageCertificate() + "?type=certificate"
                    );
                }
                userDTO.setPatient(patient);
                return userDTO;
            } else {
                return userDTO;
            }
        }
        throw new AccountResourceException("User could not be found");
    }

    @Transactional(readOnly = true)
    public Page<UserInfoDTO> getAllManagedUsersAndAuthor(Pageable pageable, String authorities) {
        List<UserInfoDTO> users = userRepository
            .findAllByAuthority(authorities, pageable.getOffset(), pageable.getPageSize())
            .stream()
            .map(UserInfoDTO::new)
            .collect(Collectors.toList());
        int total = userRepository.countAllByAuthority(authorities);
        users.forEach(
            elm -> {
                Optional<Patient> patient = patientRepository.findOneByUserId(elm.getId());
                if (patient.isPresent()) {
                    Assignment assignment = assignmentRepository.findTopByPatientIdAndPatientStatus(
                        patient.get().getId(),
                        AssignmentStatus.ACCEPT
                    );
                    elm.setAssignmentId(assignment != null ? assignment.getId() : null);
                    elm.setPatient(patient.get());
                }
            }
        );
        return new PageImpl<>(users, pageable, total);
    }

    @Transactional(readOnly = true)
    public List<SearchDoctorResponse> getAllDoctor(Pageable pageable, String search) {
        List<SearchDoctorResponse> users = userRepositoryCustom.findAllDoctor(pageable.getOffset(), pageable.getPageSize(), search);
        users.forEach(
            elm -> {
                elm.setNumberPatients(userRepositoryCustom.countAllPatients(elm.getId(), null, AssignmentStatus.ACCEPT));
                elm.setNumberCaredPatients(userRepositoryCustom.countAllPatients(elm.getId()));
            }
        );
        return users;
    }

    @Transactional(readOnly = true)
    public Integer countAllDoctor(String search) {
        return userRepositoryCustom.countAllDoctor(search);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(
                user -> {
                    log.debug("Deleting not activated user {}", user.getLogin());
                    userRepository.delete(user);
                }
            );
    }

    /**
     * Gets a list of all the authorities.
     *
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    public List<String> getDailyStatuses() {
        return DailyHealthStatus.listDailyHealthStatus;
    }
}
