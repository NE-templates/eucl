package com.trex.eucl.enums;

public enum Permissions {

    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_CREATE("customer:create");

    private final String permission;

    // Explicit constructor
    Permissions(String permission) {
        this.permission = permission;
    }

    // Explicit getter
    public String getPermission() {
        return permission;
    }
}