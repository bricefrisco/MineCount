package com.minecount.models;

public enum BucketInterval {
    TEN_MINUTES("10m"),
    ONE_HOUR("1h"),
    FOUR_HOURS("4h"),
    ONE_DAY("1d");

    private final String name;

    BucketInterval(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BucketInterval fromString(String name) {
        if (name == null) {
            return null;
        }

        for (BucketInterval bucket : BucketInterval.values()) {
            if (bucket.name.equalsIgnoreCase(name)) {
                return bucket;
            }
        }

        return null;
    }
}
