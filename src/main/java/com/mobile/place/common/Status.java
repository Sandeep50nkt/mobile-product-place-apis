package com.mobile.place.common;

import java.util.Optional;

public enum Status {
    BOOK(false),
    RETURN(true);

    private Boolean available;

    Status(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public static Optional<Status> getStatus(final String requestedStatus) {
        for (Status currentStatus : values()) {
            if (currentStatus.name().equalsIgnoreCase(requestedStatus)) {
                return Optional.of(currentStatus);
            }
        }
        return Optional.empty();
    }
}
