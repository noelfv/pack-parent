package com.bbva.batch.domain;

import java.io.Serializable;

public class EntityBatch implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long version;

    public EntityBatch() {
        super();
    }

    public EntityBatch(Long id, Long version) {
        super();
        this.id = id;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
