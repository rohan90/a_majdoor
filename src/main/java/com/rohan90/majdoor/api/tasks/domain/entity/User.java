package com.rohan90.majdoor.api.tasks.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class User {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
