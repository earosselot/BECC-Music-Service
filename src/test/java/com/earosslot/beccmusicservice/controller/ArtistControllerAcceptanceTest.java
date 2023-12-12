package com.earosslot.beccmusicservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getDetails() throws Exception {

        String mjMbid = "f27ec8db-af05-4f36-916e-3d57f91ecf5e";
        mockMvc.perform(get("/music-artist/details/" + mjMbid))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.mbid", is(mjMbid)))
                .andExpect(jsonPath("$.name", is("Michael Jackson")))
                .andExpect(jsonPath("$.gender", is("Male")))
                .andExpect(jsonPath("$.country", is("US")))
                .andExpect(jsonPath("$.disambiguation", is("“King of Pop”")))
                .andExpect(jsonPath("$.description", is("<p><b>Michael Joseph Jackson</b> was an American singer, songwriter, dancer, and philanthropist. Known as the \"King of Pop\", he is regarded as one of the most significant cultural figures of the 20th century. During his four-decade career, his contributions to music, dance, and fashion, along with his publicized personal life, made him a global figure in popular culture. Jackson influenced artists across many music genres. Through stage and video performances, he popularized complicated street dance moves such as the moonwalk, which he named, as well as the robot.</p>")))
                .andExpect(jsonPath("$.albums[0].id", is("97e0014d-a267-33a0-a868-bb4e2552918a")))
                .andExpect(jsonPath("$.albums[0].title", is("Got to Be There")))
                .andExpect(jsonPath("$.albums[0].imageUrl", is("http://coverartarchive.org/release/7d65853b-d547-4885-86a6-51df4005768c/1619682960.jpg")));
    }
}