package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.UserDetailDTO;
import com.mycompany.myapp.service.dto.UserInfoDTO;
import com.mycompany.myapp.service.dto.UserPatientDTO;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    @Query(
        value = "select ju.* from user ju join user_authority jua on ju.id = jua.user_id where jua.authority_name = :authority_name limit :offset , :limit",
        nativeQuery = true
    )
    List<User> findAllByAuthority(@Param("authority_name") String authority, @Param("offset") Long offset, @Param("limit") Integer limit);

    @Query(
        nativeQuery = true,
        value = "select count(*) from user ju join user_authority jua on ju.id = jua.user_id where jua.authority_name = :authority_name"
    )
    Integer countAllByAuthority(@Param("authority_name") String authority);

    @Query(
        value = "select ju.last_name, ju.first_name, ju.dob, ju.gender, ju.email, ju.phone_number, " +
        " a.patient_status as patientStatus from assignment a join patient pt on a.patient_id = pt.id join user ju on ju.id = pt.user_id where a.doctor_id = :doctorId and a.patient_status = :patientStatus limit :offset , :limit",
        nativeQuery = true
    )
    List<UserPatientDTO> findAllPatients(
        @Param("doctorId") Long doctorId,
        @Param("patientStatus") String patientStatus,
        @Param("offset") Long offset,
        @Param("limit") Integer limit
    );

    @Query(
        value = "select count(ju.*) from assignment a join patient pt on a.patient_id = pt.id join user ju on ju.id = pt.user_id where a.doctor_id = :doctorId and a.patient_status = :patientStatus",
        nativeQuery = true
    )
    Integer countAllPatients(@Param("doctorId") Long doctorId, @Param("patientStatus") String patientStatus);

    List<User> findAllByAuthoritiesContains(Authority authority);
}
