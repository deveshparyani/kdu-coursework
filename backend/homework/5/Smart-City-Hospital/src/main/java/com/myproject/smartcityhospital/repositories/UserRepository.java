package com.myproject.smartcityhospital.repositories;

import com.myproject.smartcityhospital.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user){
        String sql = """
               INSERT INTO users(id, username, timezone, tenant_id)
               VALUES(?,?,?,?)
        """;

        jdbcTemplate.update(
                sql,
                user.getId(),
                user.getUsername(),
                user.getTimezone(),
                user.getTenantId()
        );
    }


    public void updateUsername(UUID userId, String username) {
        jdbcTemplate.update(
                "UPDATE users SET username = ? WHERE id = ?",
                username,
                userId
        );
    }

    public List<User> findByTenantPaginated(
            UUID tenantId,
            int page,
            int size,
            String direction
    ) {
        int offset = page * size;
        String order = direction.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        String sql = """
            SELECT id, username, timezone, tenant_id
            FROM users
            WHERE tenant_id = ?
            ORDER BY username %s
            LIMIT ? OFFSET ?
        """.formatted(order);

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new User(
                        rs.getObject("id", UUID.class),
                        rs.getString("username"),
                        rs.getString("timezone"),
                        rs.getObject("tenant_id", UUID.class)
                ),
                tenantId,
                size,
                offset
        );
    }


}
