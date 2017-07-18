package com.assignment.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by manish on 17/07/17.
 */
@Entity
@Table(name="playlist")
@Data
public class Playlist extends BaseEntity {

    @Lob
    @NotNull
    private String name;

    @Lob
    @NotNull
    private String authorName;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

    @Min(0)
    private Long likeCount = 0L;

    @Min(0)
    private Long playCount = 0L;
}
