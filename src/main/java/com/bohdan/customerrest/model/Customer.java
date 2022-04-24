package com.bohdan.customerrest.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint")
    Long id;

    @Column(columnDefinition = "bigint")
    Integer created;

    @Column(columnDefinition = "bigint")
    Integer updated;

    String fullName;

    @Column(unique = true)
    String email;

    String phone;

    @Column(columnDefinition = "boolean default true")
    Boolean isActive;

}
