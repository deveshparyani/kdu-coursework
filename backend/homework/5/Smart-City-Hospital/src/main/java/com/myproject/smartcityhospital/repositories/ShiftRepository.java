package com.myproject.smartcityhospital.repositories;

import com.myproject.smartcityhospital.model.Shift;
import com.myproject.smartcityhospital.model.ShiftType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ShiftRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShiftRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveShiftType(ShiftType shiftType) {
        jdbcTemplate.update(
                """
                INSERT INTO shift_type (id, name, description, tenant_id)
                VALUES (?, ?, ?, ?)
                """,
                shiftType.getId(),
                shiftType.getName(),
                shiftType.getDescription(),
                shiftType.getTenantId()
        );
    }

    public void saveShift(Shift shift) {
        jdbcTemplate.update(
                """
                INSERT INTO shift (id, shift_type_id, tenant_id)
                VALUES (?, ?, ?)
                """,
                shift.getId(),
                shift.getShiftTypeId(),
                shift.getTenantId()
        );
    }

    public List<Shift> findShiftsByTenant(UUID tenantId) {

        String sql = """
        SELECT id, shift_type_id, tenant_id
        FROM shift
        WHERE tenant_id = ?
    """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Shift(
                        rs.getObject("id", UUID.class),
                        rs.getObject("shift_type_id", UUID.class),
                        rs.getObject("tenant_id", UUID.class)
                ),
                tenantId
        );
    }
}
