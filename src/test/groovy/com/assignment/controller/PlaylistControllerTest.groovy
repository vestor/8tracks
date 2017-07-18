package com.assignment.controller

import com.assignment.entity.Playlist
import com.assignment.entity.Tag
import com.assignment.impl.PlaylistServiceImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.any
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
/**
 * Created by manish on 18/07/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PlaylistController.class)
public class PlaylistControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("custom");

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

    }

    @MockBean
    private PlaylistServiceImpl service;

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {

        Playlist playlist = new Playlist(name: 'name1', authorName: 'authorName1', likeCount: 10L, playCount: 100L, tags: [new Tag('t1'), new Tag('t2')]);

        Page<Playlist> allPlaylists = new PageImpl<Playlist>(Arrays.asList(playlist))

        given(service.listAllByPage(any(Pageable))).willReturn(allPlaylists);

        mockMvc.perform(get("/playlist/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("index"))

    }
}