package com.assignment.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Created by manish on 17/07/17.
 */
@Entity
@Table(name="tag")
@Data
public class Tag {

    @Id
    @Size(min = 1, max = 32)
    private String name;

    public void setName(String name) {
        if(name != null) {
            this.name = name.toLowerCase();
        } else {
            this.name = null;
        }
    }

    public Tag(String tagName) {
        setName(tagName);
    }

    public Tag() {}
}
