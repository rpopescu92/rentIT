package com.rentIT.domain.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;
    private String name;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content;
}
