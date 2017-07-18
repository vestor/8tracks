package com.assignment.database

import com.assignment.entity.Tag
import com.assignment.repository.TagRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.Assertions.assertThat
/**
 * Created by manish on 18/07/17.
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class TagRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void findByName() {
        // given
        Set<Tag> tags = [new Tag('t1'), new Tag('t2'), new Tag('t3')]

        tags.each {entityManager.persist(it)}
        entityManager.flush();

        // when
        def tagFound = tagRepository.findOne('t1')

        // then
        assertThat(tagFound.name == 't1');
    }

    @Test
    public void findByName_Error() {
        // given
        Set<Tag> tags = [new Tag('t1'), new Tag('t2'), new Tag('t3')]

        tags.each {entityManager.persist(it)}
        entityManager.flush();

        // when
        def tagFound = tagRepository.findOne('t4')

        // then
        assertThat(tagFound == null);
    }




}
