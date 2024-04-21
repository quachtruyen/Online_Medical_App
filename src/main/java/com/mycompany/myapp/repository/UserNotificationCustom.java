package com.mycompany.myapp.repository;

import com.mycompany.myapp.constant.AssignmentStatus;
import com.mycompany.myapp.domain.Assignment;
import com.mycompany.myapp.domain.Notification;
import com.mycompany.myapp.service.dto.NotificationDTO;
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
public class UserNotificationCustom {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final AssignmentRepository assignmentRepository;

    public UserNotificationCustom(NamedParameterJdbcTemplate namedParameterJdbcTemplate, AssignmentRepository assignmentRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.assignmentRepository = assignmentRepository;
    }

    public List<NotificationDTO> findAllByFilter(Long userId, String search, String status, Integer limit, Long offset) {
        String sql =
            "select n.id, un.id as userNotificationId, n.title, n.content, un.status, un.created_at, n.url" +
            " from user_notification un join notification n on n.id = un.notification_id where true %s order by un.created_at desc limit :offset , :limit";
        MapSqlParameterSource params = new MapSqlParameterSource();

        String data = buildWhere(userId, search, status, params);
        sql = String.format(sql, data);
        params.addValue("limit", limit);
        params.addValue("offset", offset);
        List<NotificationDTO> result = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(NotificationDTO.class));
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

    public Integer countAllByFilter(Long userId, String healthStatus, String patientStatus) {
        String sql = "select count(n.id) from user_notification un join notification n on n.id = un.notification_id where true %s";
        MapSqlParameterSource params = new MapSqlParameterSource();
        String data = buildWhere(userId, healthStatus, patientStatus, params);
        sql = String.format(sql, data);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}
