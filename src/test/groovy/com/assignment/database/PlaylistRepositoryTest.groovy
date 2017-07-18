package com.assignment.database

import com.assignment.entity.Playlist
import com.assignment.entity.Tag
import com.assignment.repository.PlaylistRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.Assertions.assertThat

/**
 * Created by manish on 18/07/17.
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlaylistRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    public void whenFindRelatedTags_thenReturnPlaylists() {
        // given
        Set<Tag> tags = [new Tag('t1'), new Tag('t2'), new Tag('t3')]
        Playlist playlist = new Playlist(name: 'name1', authorName: 'authorName1', likeCount: 10L, playCount: 100L, tags: [new Tag('t1'), new Tag('t2')]);
        Playlist playlist2 = new Playlist(name: 'name2', authorName: 'authorName2', likeCount: 1L, playCount: 2L, tags: [new Tag('t3'), new Tag('t2')]);

        entityManager.persist(playlist);
        entityManager.persist(playlist2);
        tags.each {entityManager.persist(it)}
        entityManager.flush();

        // when
        def tagsFound = playlistRepository.findRelatedTags(new HashSet<>(['t1']), 1)

        // then
        assertThat(tagsFound.containsAll(['t1','t2']));
    }


    @Test
    public void whenFindByTags_thenReturnPlaylists() {
        // given
        Set<Tag> tags = [new Tag('t1'), new Tag('t2'), new Tag('t3')]
        Playlist playlist = new Playlist(name: 'name1', authorName: 'authorName1', likeCount: 10L, playCount: 100L, tags: [new Tag('t1'), new Tag('t2')]);
        Playlist playlist2 = new Playlist(name: 'name2', authorName: 'authorName2', likeCount: 1L, playCount: 2L, tags: [new Tag('t3'), new Tag('t2')]);

        entityManager.persist(playlist);
        entityManager.persist(playlist2);
        tags.each {entityManager.persist(it)}
        entityManager.flush();

        // when
        def playlistFound1 = playlistRepository.findByTags(new HashSet<>(['t1']), 1, new PageRequest(0,20))
        def playlistFound2 = playlistRepository.findByTags(new HashSet<>(['t1','t2']), 1, new PageRequest(0,20))

        // then
        assertThat(playlistFound1.size == 1);
        assertThat(playlistFound1[0].name == playlist.name);

        assertThat(playlistFound2.size == 2);
        assertThat(playlistFound2[0].name == playlist.name);
    }

}
