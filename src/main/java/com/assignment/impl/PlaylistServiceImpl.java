package com.assignment.impl;

import com.assignment.entity.Playlist;
import com.assignment.entity.Tag;
import com.assignment.exception.AssignmentException;
import com.assignment.model.PlaylistExploreResponse;
import com.assignment.repository.PlaylistRepository;
import com.assignment.repository.TagRepository;
import com.assignment.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by manish on 18/07/17.
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final TagRepository tagRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, TagRepository tagRepository) {
        this.playlistRepository = playlistRepository;
        this.tagRepository = tagRepository;
    }


    @Override
    public Playlist save(Playlist playlist) {
        tagRepository.save(playlist.getTags());
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist update(Playlist playlist, Long id) {
        if(id == null) {
            throw new AssignmentException("Playlist ID cannot be null in Update", HttpStatus.BAD_REQUEST);
        }
        tagRepository.save(playlist.getTags());
        playlist.setId(id);
        return playlistRepository.save(playlist);
    }

    @Override
    public void delete(Long playlistId) {
        playlistRepository.delete(playlistId);
    }

    @Override
    public Playlist findOne(@NotNull Long playlistId) {
        if(playlistId == null) {
            throw new AssignmentException("Playlist ID cannot be null in Get", HttpStatus.BAD_REQUEST);
        }
        Playlist one = playlistRepository.findOne(playlistId);
        if(one == null) {
            throw new AssignmentException("Playlist with this Id not found", HttpStatus.BAD_REQUEST);
        }
        return one;
    }

    @Override
    public Page<Playlist> listAllByPage(Pageable pageable) {
        return playlistRepository.findAll(pageable);
    }

    @Override
    public PlaylistExploreResponse explore(List<String> tagNames, Pageable pageable) {
        if(tagNames == null || tagNames.isEmpty()) {
            throw new AssignmentException("Tag Names cannot be empty", HttpStatus.BAD_REQUEST);
        }

        Set<String> tagSet = tagNames.stream().collect(Collectors.toSet());
        Page<Playlist> playlists = playlistRepository.findByTags(tagSet, tagSet.size(), pageable);
        PlaylistExploreResponse response = new PlaylistExploreResponse();
        response.setPlaylists(playlists);

        Set<Tag> tags = playlistRepository.findRelatedTags(tagSet, tagSet.size());
        response.setTags(tags.stream().filter(a -> !tagNames.contains(a.getName())).collect(Collectors.toSet()));
        return response;
    }
}
