package com.assignment.repository;

import com.assignment.entity.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by manish on 18/07/17.
 */
public interface TagRepository extends PagingAndSortingRepository<Tag, String> {
}
