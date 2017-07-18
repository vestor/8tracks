package com.assignment.controller;

import com.assignment.entity.Playlist;
import com.assignment.model.ErrorResponse;
import com.assignment.model.PlaylistExploreResponse;
import com.assignment.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Created by manish on 18/07/17.
 */
@RestController
@RequestMapping(value = "/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Playlist createPlaylist(@RequestBody @Valid Playlist playlist) {
        return playlistService.save(playlist);
    }

    @RequestMapping(value = "/{playlistId}", method = RequestMethod.PUT)
    public Playlist updatePlaylist(@PathVariable Long playlistId, @RequestBody @Valid Playlist playlist) {
        return playlistService.update(playlist, playlistId);
    }

    @RequestMapping(value = "/{playlistId}", method = RequestMethod.DELETE)
    public void deletePlaylist(@PathVariable Long playlistId) {
        playlistService.delete(playlistId);
    }

    @RequestMapping(value = "/{playlistId}", method = RequestMethod.GET)
    public Playlist getPlayList(@PathVariable Long playlistId) {
        return playlistService.findOne(playlistId);
    }

    @RequestMapping(value = {"", "/"})
    public Page<Playlist> list(Pageable pageable){
        return playlistService.listAllByPage(pageable);
    }

    @RequestMapping(value = "/explore/{tagNames}", method = RequestMethod.GET)
    public PlaylistExploreResponse explore(@PathVariable String[] tagNames, Pageable pageable) {
        return playlistService.explore(Arrays.asList(tagNames), pageable);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        return new ResponseEntity<>(new ErrorResponse(fieldErrors.stream().map(a -> "Invalid value: " + a.getRejectedValue() + " for field: " + a.getField()).reduce("", (b, c) -> b + "," + c), ex), HttpStatus.BAD_REQUEST);
    }
}
