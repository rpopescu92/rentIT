package com.rentIT.domain.model;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private long id;
    private String text;
    private User to;
    private User from;
    private Date date;
}
