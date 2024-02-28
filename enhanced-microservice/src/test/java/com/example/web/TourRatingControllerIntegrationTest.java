package com.example.web;

import com.example.domain.Tour;
import com.example.domain.TourRating;
import com.example.service.TourRatingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;

import static com.example.service.utils.TestUtils.COMMENT;
import static com.example.service.utils.TestUtils.CUSTOMER_ID;
import static com.example.service.utils.TestUtils.SCORE;
import static com.example.service.utils.TestUtils.TOUR_ID;
import static com.example.service.utils.TestUtils.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TourRatingControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private TourRatingService tourRatingService;

    @Mock
    private TourRating tourRating;

    @Mock
    private Tour tour;

    @Test
    void testCreateRating() {
        RatingDto ratingDto = new RatingDto(SCORE, COMMENT, CUSTOMER_ID);

        HttpStatusCode statusCode = testRestTemplate
                .postForEntity(URL, ratingDto, Void.class, TOUR_ID)
                .getStatusCode();

        assertEquals(HttpStatusCode.valueOf(201), statusCode);
    }

    @Test
    void testCreateManyTourRatings() {
        HttpStatusCode statusCode = testRestTemplate
                .postForEntity(URL + "/{score}?customers=" + CUSTOMER_ID, null, Void.class, TOUR_ID, SCORE)
                .getStatusCode();
        assertEquals(HttpStatusCode.valueOf(201), statusCode);
    }
}