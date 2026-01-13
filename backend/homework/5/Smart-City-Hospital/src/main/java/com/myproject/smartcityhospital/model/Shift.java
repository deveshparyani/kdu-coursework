package com.myproject.smartcityhospital.model;

import java.util.UUID;

public class Shift {

    private UUID id;
    private UUID shiftTypeId;
    private UUID tenantId;

    public Shift(UUID id, UUID shiftTypeId, UUID tenantId) {
        this.id = id;
        this.shiftTypeId = shiftTypeId;
        this.tenantId = tenantId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getShiftTypeId() {
        return shiftTypeId;
    }

    public UUID getTenantId() {
        return tenantId;
    }
}

