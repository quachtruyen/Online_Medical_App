package com.mycompany.myapp.repository;

import com.mycompany.myapp.service.dto.DailyHealthStatusDTO;
import com.mycompany.myapp.service.dto.NotificationDTO;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DailyHealthStatusRepositoryCustom {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DailyHealthStatusRepositoryCustom(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<DailyHealthStatusDTO> findAllByPatient(Long patientId, Integer limit, Long offset) {
        String sql =
            "select dh.* from daily_health_status dh " +
            " where dh.patient_id = :patientId order by dh.created_at desc limit :offset , :limit";
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("limit", limit);
        params.addValue("offset", offset);
        params.addValue("patientId", patientId);
        List<DailyHealthStatusDTO> result = namedParameterJdbcTemplate.query(
            sql,
            params,
            new BeanPropertyRowMapper<>(DailyHealthStatusDTO.class)
        );
        return result;
    }

    private String buildWhere(Long userId, String search, String status, MapSqlParameterSource params) {
        String data = "";
        if (StringUtils.isNotBlank(search)) {
            data += " and n.title like :search ";
            params.addValue("search", "%" + search + "%");
        }
        if (userId != null) {
            data += " and un.user_id = :userId ";
            params.addValue("userId", userId);
        }
        if (StringUtils.isNotBlank(status)) {
            data += " and un.status = :status  ";
            params.addValue("status", status);
        }
        return data;
    }

    public Integer countAllByPatient(Long patientId) {
        String sql = "select count(dh.id) from daily_health_status dh where dh.patient_id = :patientId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("patientId", patientId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}
