package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Post;
import com.mycompany.myapp.service.dto.DailyHealthStatusDTO;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryCustom {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostRepositoryCustom(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Post> findAllByCategoryId(Long categoryId, Integer limit, Long offset) {
        String sql =
            "select p.* from post p join post_category pc on p.id = pc.id_post where " +
            "pc.id_category = :categoryId and p.published = true order by p.created_at limit :offset, :limit";
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("limit", limit);
        params.addValue("offset", offset);
        params.addValue("categoryId", categoryId);
        List<Post> result = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Post.class));
        return result;
    }

    public Integer countAllByCategoryId(Long categoryId) {
        String sql =
            "select count(distinct pc.id_post) from post_category pc join post p on p.id = pc.id_post where pc.id_category = :categoryId and p.published = true ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("categoryId", categoryId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}
