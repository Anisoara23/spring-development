package com.example.web;

import com.example.service.TourRatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.service.utils.TestUtils.COMMENT;
import static com.example.service.utils.TestUtils.CUSTOMER_ID;
import static com.example.service.utils.TestUtils.SCORE;
import static com.example.service.utils.TestUtils.TOUR_ID;
import static com.example.service.utils.TestUtils.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TourRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TourRatingService tourRatingService;

    @MockBean
    private RatingAssembler ratingAssembler;

    @Test
    void testCreateTourRating() throws Exception {
        RatingDto ratingDto = new RatingDto(SCORE, COMMENT, CUSTOMER_ID);
        mockMvc.perform(post(URL, TOUR_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDto)))
                .andExpect(status().isCreated()).andReturn()
                .getResponse();
    }

    @Test
    void testCreateManyTourRatings() throws Exception {
        mockMvc.perform(post(URL + "/{score}", TOUR_ID, SCORE)
                        .param("customers", String.valueOf(CUSTOMER_ID)))
                .andExpect(status().isCreated());
    }
}