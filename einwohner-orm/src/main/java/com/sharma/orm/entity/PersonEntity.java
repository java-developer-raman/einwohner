package com.sharma.orm.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "persons")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PersonEntity extends AbstractEntity {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;
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
