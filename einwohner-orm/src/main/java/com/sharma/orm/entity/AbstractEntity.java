package com.sharma.orm.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity {
    public static final String SYSTEM = "System";
    @Version
    @Column(name = "version")
    protected Integer version;
    @Column(name = "created_at")
    protected Date createdAt;
    @Column(name = "created_by")
    protected String createdBy;
    @Column(name = "modified_at")
    protected Date modifiedAt;
    @Column(name = "modified_by")
    protected String modifiedBy;

    @PrePersist
    protected void beforeInsert(){
        createdAt = new Date();
        createdBy = SYSTEM;
    }

    @PreUpdate
    protected void beforeModify(){
        modifiedAt = new Date();
        modifiedBy = SYSTEM;
    }
}
