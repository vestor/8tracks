package com.assignment.controller;

import com.assignment.entity.Tag;
import com.assignment.model.ErrorResponse;
import com.assignment.service.BaseService;
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
import java.util.List;

/**
 * Created by manish on 18/07/17.
 */
@RestController
@RequestMapping(value = "/tag")
public class TagController {

    private final BaseService<Tag, String> tagService;

    @Autowired
    public TagController(BaseService<Tag, String> tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Tag createTag(@RequestBody @Valid Tag tag) {
        return tagService.save(tag);
    }

    @RequestMapping(value="/{tagId}", method = RequestMethod.PUT)
    public Tag updateTag(@PathVariable String tagId, @RequestBody @Valid Tag tag) {
        return tagService.update(tag, tagId);
    }

    @RequestMapping(value = "/{tagId}", method = RequestMethod.DELETE)
    public void deleteTag(@PathVariable String tagId) {
        tagService.delete(tagId);
    }

    @RequestMapping(value = "/{tagId}", method = RequestMethod.GET)
    public Tag getPlayList(@PathVariable String tagId) {
        return tagService.findOne(tagId);
    }

    @RequestMapping(value = {"", "/"})
    public Page<Tag> list(Pageable pageable){
        return tagService.listAllByPage(pageable);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        return new ResponseEntity<>(new ErrorResponse(fieldErrors.stream().map(a -> "Invalid value: " + a.getRejectedValue() + " for field: " + a.getField()).reduce("", (b, c) -> b + "," + c), ex), HttpStatus.BAD_REQUEST);
    }

}
