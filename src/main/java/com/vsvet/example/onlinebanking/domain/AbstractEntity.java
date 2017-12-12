package com.vsvet.example.onlinebanking.domain;

import javax.persistence.*;
import java.time.Instant;

/**
 * All our entities will have : createdDate and modifiedDate.
 * Also for optimistic locking will use version field.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Version
    @Column(name = "version")
    private Long version;

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @PrePersist
    public void onCreate(){
        this.createdDate = Instant.now();
        this.modifiedDate = Instant.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.modifiedDate = Instant.now();
    }


}
