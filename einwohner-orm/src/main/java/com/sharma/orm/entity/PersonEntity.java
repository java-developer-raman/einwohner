package com.sharma.orm.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@ToString
@NoArgsConstructor
@Builder
public class PersonEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name = "vor_name", nullable = false)
    private String vorName;
    @Column(name = "nach_name", nullable = false)
    private String nachName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "telefon_nummer")
    private String telefonNummer;
    @Column(name = "handy_nummer")
    private String handyNummer;
}
