package com.mycompany.myapp.repository;

import com.mycompany.myapp.constant.AssignmentStatus;
import com.mycompany.myapp.domain.Assignment;
import com.mycompany.myapp.service.dto.UserPatientDTO;
import com.mycompany.myapp.service.patientdto.SearchDoctorResponse;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class UserRepositoryCustom {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final AssignmentRepository assignmentRepository;

    public UserRepositoryCustom(NamedParameterJdbcTemplate namedParameterJdbcTemplate, AssignmentRepository assignmentRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.assignmentRepository = assignmentRepository;
    }

    public List<UserPatientDTO> findAllPatients(Long doctorId, String healthStatus, String patientStatus, Integer limit, Long offset) {
        String sql =
            "select pt.id, pt.address, ju.login, ju.activated, ju.last_name, ju.first_name, ju.dob, ju.gender, ju.email, ju.phone_number, " +
            " a.patient_status as patientStatus, pt.health_status from user ju join patient pt on ju.id = pt.user_id left join assignment a on a.patient_id = pt.id where true %s limit :offset , :limit";
        MapSqlParameterSource params = new MapSqlParameterSource();

        String data = buildWhere(doctorId, healthStatus, patientStatus, params);
        sql = String.format(sql, data);
        params.addValue("limit", limit);
        params.addValue("offset", offset);
        List<UserPatientDTO> result = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(UserPatientDTO.class));
        result.forEach(
            elm -> {
                Assignment assignment = assignmentRepository.findTopByPatientIdAndPatientStatus(elm.getId(), AssignmentStatus.ACCEPT);
                elm.setAssignment(assignment);
            }
        );
        return result;
    }

    private String buildWhere(Long doctorId, String healthStatus, String patientStatus, MapSqlParameterSource params) {
        String data = "";
        if (StringUtils.isNotBlank(patientStatus)) {
            data += " and a.patient_status = :patientStatus ";
            params.addValue("patientStatus", patientStatus);
        }
        if (StringUtils.isNotBlank(healthStatus)) {
            data += " and pt.health_status = :healthStatus ";
            params.addValue("healthStatus", healthStatus);
        }
        if (doctorId != null) {
            data += " and a.doctor_id = :doctorId  ";
            params.addValue("doctorId", doctorId);
        }
        return data;
    }

    public Integer countAllPatients(Long doctorId, String healthStatus, String patientStatus) {
        String sql =
            "select count(pt.id) from user ju join patient pt on ju.id = pt.user_id left join assignment a on a.patient_id = pt.id where true %s";
        MapSqlParameterSource params = new MapSqlParameterSource();
        String data = buildWhere(doctorId, healthStatus, patientStatus, params);
        sql = String.format(sql, data);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Integer countAllPatients(Long doctorId) {
        String sql =
            "select count(pt.id) from assignment a join patient pt on a.patient_id = pt.id join user ju on ju.id = pt.user_id where a.doctor_id = :doctorId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("doctorId", doctorId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public UserPatientDTO findOneByPatientId(Long patientId) {
        String sql =
            "select pt.image_certificate, pt.id,  pt.address, ju.login, ju.activated, ju.last_name, ju.first_name, ju.dob, ju.gender, ju.email, ju.phone_number, " +
            " pt.health_status from patient pt join user ju on ju.id = pt.user_id where pt.id = :patientId limit 0, 1";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("patientId", patientId);
        List<UserPatientDTO> result = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(UserPatientDTO.class));
        return CollectionUtils.isEmpty(result) ? new UserPatientDTO() : result.get(0);
    }

    public List<SearchDoctorResponse> findAllDoctor(long offset, int limit, String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        String sql = "select d.id,  ju.last_name, ju.first_name from doctor d join user ju on d.user_id = ju.id where ju.activated = true";
        if (StringUtils.isNotBlank(search)) {
            sql += " and (ju.last_name like :search or ju.first_name like :search) ";
            params.addValue("search", "%" + search + "%");
        }
        sql += " limit :offset , :limit";
        params.addValue("limit", limit);
        params.addValue("offset", offset);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(SearchDoctorResponse.class));
    }

    public Integer countAllDoctor(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        String sql = "select count(d.id) from doctor d join user ju on d.user_id = ju.id where ju.activated = true";
        if (StringUtils.isNotBlank(search)) {
            sql += " and (ju.last_name like :search or ju.first_name like :search) ";
            params.addValue("search", "%" + search + "%");
        }

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}
