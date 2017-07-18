package com.assignment.impl;

import com.assignment.entity.Tag;
import com.assignment.repository.TagRepository;
import com.assignment.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by manish on 18/07/17.
 */
@Service
public class TagServiceImpl implements BaseService<Tag, String> {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(Tag tag, String id) {
        throw new IllegalArgumentException("Operation not supported");
    }

    @Override
    public void delete(String tagId) {
        throw new IllegalArgumentException("Operation Not Supported");
    }

    @Override
    public Tag findOne(String tagId) {
        if(tagId == null) {
            throw new IllegalArgumentException("Tag ID cannot be null in Get");
        }
        return tagRepository.findOne(tagId);
    }

    @Override
    public Page<Tag> listAllByPage(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
}
