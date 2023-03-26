package by.karpovich.shop.jpa.entity;

import java.util.Optional;

public enum StatusOrganization {

    ACTIVE, FROZEN, DELETED, NOT_VALID;

    public static Optional<StatusOrganization> from(String stringStatus) {
        for (StatusOrganization status : values()) {
            if (status.name().equalsIgnoreCase(stringStatus)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }
}
