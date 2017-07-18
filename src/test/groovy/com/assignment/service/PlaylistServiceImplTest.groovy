package com.assignment.service

import com.assignment.entity.Playlist
import com.assignment.entity.Tag
import com.assignment.impl.PlaylistServiceImpl
import com.assignment.repository.PlaylistRepository
import com.assignment.repository.TagRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Matchers.any
import static org.mockito.Mockito.atLeastOnce
import static org.mockito.Mockito.atMost

/**
 * Created by manish on 18/07/17.
 */
@RunWith(SpringRunner.class)
public class PlaylistServiceImplTest {

    private PlaylistService playlistService;

    @MockBean
    private PlaylistRepository playlistRepository;

    @MockBean
    private TagRepository tagRepository;

    @Before
    public void setUp() {
        this.playlistService = new PlaylistServiceImpl(playlistRepository, tagRepository);
    }


    @Test
    public void testSave(){
        // given
        Playlist playlist = new Playlist(name: 'name1', authorName: 'authorName1', likeCount: 10L, playCount: 100L, tags: [new Tag('t1'), new Tag('t2')]);

        //when
        Mockito.when(playlistRepository.save(any())).thenReturn(new Playlist(id: 1L));
        def playlistSaved = playlistService.save(playlist)

        //then
        Mockito.verify(tagRepository, atMost(1)).save(playlist.getTags())
        Mockito.verify(playlistRepository, atMost(1)).save(playlist)

        //when
        playlist.authorName = 'newAName'
        playlistService.update(playlist, playlistSaved.id)

        //then
        Mockito.verify(tagRepository, atMost(1)).save(playlist.getTags())
        playlist.id == 1L;
        Mockito.verify(playlistRepository, atMost(1)).save(playlist)
    }

    @Test
    public void testUpdate(){
        // given
        Playlist playlist = new Playlist(name: 'name1', authorName: 'authorName1', likeCount: 10L, playCount: 100L, tags: [new Tag('t1'), new Tag('t2')]);

        //when
        Mockito.when(playlistRepository.save(any())).thenReturn(new Playlist(id: 1L));
        def playlistSaved = playlistService.save(playlist)

        playlist.authorName = 'newAName'
        playlistService.update(playlist, playlistSaved.id)

        //then
        Mockito.verify(tagRepository, atMost(2)).save(playlist.getTags())
        assertThat(playlist.id == 1L)
        Mockito.verify(playlistRepository, atMost(2)).save(playlist)
    }


    @Test
    public void testExplore(){
        // given
        Playlist playlist = new Playlist(name: 'name1', authorName: 'authorName1', likeCount: 10L, playCount: 100L, tags: [new Tag('t1'), new Tag('t2')]);
        Playlist playlist2 = new Playlist(name: 'name2', authorName: 'authorName2', likeCount: 1L, playCount: 10L, tags: [new Tag('t3'), new Tag('t2')]);

        //when
        def request = new PageRequest(0, 20)
        def response = playlistService.explore(['t1', 't2'], request)
        def set = new HashSet<>(['t1', 't2'])
        Mockito.when(playlistRepository.findByTags(set,2, request)).thenReturn(new PageImpl<Playlist>([playlist, playlist2]))
        Mockito.when(playlistRepository.findRelatedTags(set,2)).thenReturn(new HashSet<>([new Tag('t1'), new Tag('t3'), new Tag('t2')]))


        //then
        Mockito.verify(playlistRepository, atLeastOnce()).findByTags(set, 2, request);
        assertThat(response.playlists.content.size() == 2)
        assertThat(response.tags.size() == 1)
        assertThat(response.tags[0].name == 't3')

    }
}