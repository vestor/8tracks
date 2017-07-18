package com.assignment.model;

import com.assignment.entity.Playlist;
import com.assignment.entity.Tag;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * Created by manish on 18/07/17.
 */
@Data
public class PlaylistExploreResponse {

    Page<Playlist> playlists;
    Set<Tag> tags;
}
