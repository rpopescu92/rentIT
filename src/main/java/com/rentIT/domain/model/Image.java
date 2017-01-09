package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String path;

    @ManyToOne
    @JoinColumn (name="property_id",referencedColumnName="id",nullable=false,unique=true)
    private Property property;
}
