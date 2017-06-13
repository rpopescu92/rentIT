package com.rentIT.domain.model;

public enum Status {

    RENTED("rented"), NOT_RENTED("not_rented"), INTERESTED("interested");

    private String status;

    Status(String status) {
        this.status = status;
    }
}
