package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "history_renting")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryRenting {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.NOT_RENTED;

    @ManyToOne
    private Property property;

    private String dateRented;

    //maybe add a tenant. for now it's ok without the tenant
}
