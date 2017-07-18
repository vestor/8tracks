package com.assignment.service;

import com.assignment.entity.Playlist;
import com.assignment.model.PlaylistExploreResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by manish on 18/07/17.
 */
public interface PlaylistService extends BaseService<Playlist, Long> {

    PlaylistExploreResponse explore(List<String> tagNames, Pageable pageable);

}
