package com.earosslot.beccmusicservice.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getDetails() throws Exception {

        String mjMbid = "f27ec8db-af05-4f36-916e-3d57f91ecf5e";
        mockMvc.perform(get("/music-artist/details/" + mjMbid))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.mbid", is(mjMbid)),
                        jsonPath("$.name", is("Michael Jackson")),
                        jsonPath("$.gender", is("Male")),
                        jsonPath("$.country", is("US")),
                        jsonPath("$.disambiguation", is("“King of Pop”")),
                        jsonPath("$.description", containsStringIgnoringCase("Michael Joseph Jackson")),
                        content().string(containsString("97e0014d-a267-33a0-a868-bb4e2552918a")),
                        content().string(containsString("Got to Be There")),
                        content().string(containsString("http://coverartarchive.org/release/7d65853b-d547-4885-86a6-51df4005768c/1619682960.jpg"))
                );
    }
}