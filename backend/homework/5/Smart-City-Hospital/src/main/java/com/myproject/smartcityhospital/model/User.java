package com.myproject.smartcityhospital.model;

import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String timezone;
    private UUID tenantId;

    public User(UUID id, String username, String timezone, UUID tenantId) {
        this.id = id;
        this.username = username;
        this.timezone = timezone;
        this.tenantId = tenantId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }
}
