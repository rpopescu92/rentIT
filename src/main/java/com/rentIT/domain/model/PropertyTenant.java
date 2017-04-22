package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "property_tenants")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyTenant {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Property property;

    @OneToOne
    private User user;

    private TenantRole tenantRole;
}
