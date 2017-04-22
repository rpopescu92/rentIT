package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "facility")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Facilities {

    @Id
    @GeneratedValue
    private long id;

    private Compartment compartment;

}
