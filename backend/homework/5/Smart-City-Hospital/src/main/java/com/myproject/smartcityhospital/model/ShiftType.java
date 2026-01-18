package com.myproject.smartcityhospital.model;

import java.util.UUID;

public class ShiftType {

    private UUID id;
    private String name;
    private String description;
    private Boolean active;
    private UUID tenantId;

    public ShiftType(UUID id, String name, String description, Boolean active, UUID tenantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.tenantId = tenantId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getActive() {
        return active;
    }

    public UUID getTenantId() {
        return tenantId;
    }
}
