package com.minecount.models;

public enum Claim {
    APPROVE_SERVERS("approve:servers"),
    UPDATE_SERVERS("update:servers");

    private final String name;

    Claim(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
