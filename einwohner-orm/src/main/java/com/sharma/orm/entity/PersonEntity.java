package com.sharma.orm.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PersonEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
