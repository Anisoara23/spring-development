package com.example.service;

import com.example.domain.TourRating;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static com.example.service.utils.TestUtils.COMMENT;
import static com.example.service.utils.TestUtils.CUSTOMER_ID;
import static com.example.service.utils.TestUtils.NEW_COMMENT;
import static com.example.service.utils.TestUtils.NEW_SCORE;
import static com.example.service.utils.TestUtils.NOT_A_TOUR_ID;
import static com.example.service.utils.TestUtils.SCORE;
import static com.example.service.utils.TestUtils.TOUR_ID;
import static com.example.service.utils.TestUtils.TOUR_RATING_ID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TourRatingServiceIntegrationTest {

    @Autowired
    private TourRatingService tourRatingService;

    @Test
    void testCreateNewRating() {
        tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);

        TourRating tourRating = tourRatingService.verifyTourRating(TOUR_ID, CUSTOMER_ID);

        assertEquals(TOUR_ID, (int) tourRating.getTour().getId());
        assertEquals(CUSTOMER_ID, (int) tourRating.getCustomerId());
        assertEquals(COMMENT, tourRating.getComment());
        assertEquals(SCORE, tourRating.getScore());
    }

    @Test
    void testCreateNewRating_whenCreateWithNonTourId_thenThrow() {
        assertThrows(NoSuchElementException.class,
                () -> tourRatingService.createNew(NOT_A_TOUR_ID, CUSTOMER_ID, SCORE, COMMENT));
    }

    @Test
    void testGetAverageScore() {
        Double averageScore = tourRatingService.getAverageScore(TOUR_ID);

        Page<TourRating> tourRatings = tourRatingService.lookupRatings(TOUR_ID, null);

        OptionalDouble average = tourRatings.toList()
                .stream()
                .mapToInt(TourRating::getScore)
                .average();

        assertEquals(average.getAsDouble(), averageScore);
    }

    @Test
    void testGetAverageScore_whenGetAverageScoreForNonTourId_thenThrow() {
        assertThrows(NoSuchElementException.class,
                () -> tourRatingService.getAverageScore(NOT_A_TOUR_ID));
    }

    @Test
    void testUpdate() {
        tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);

        TourRating updated =
                tourRatingService.update(TOUR_ID, CUSTOMER_ID, NEW_SCORE, NEW_COMMENT);

        assertEquals(NEW_SCORE, updated.getScore());
        assertEquals(NEW_COMMENT, updated.getComment());
    }

    @Test
    void testUpdate_whenUpdateWithNonTourId_thenThrow() {
        assertThrows(NoSuchElementException.class,() ->
                tourRatingService.update(TOUR_ID, CUSTOMER_ID, NEW_SCORE, NEW_COMMENT));
    }

    @Test
    void testUpdateSome() {
        tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);

        TourRating updated =
                tourRatingService.updateSome(TOUR_ID, CUSTOMER_ID, NEW_SCORE, NEW_COMMENT);

        assertEquals(NEW_SCORE, updated.getScore());
        assertEquals(NEW_COMMENT, updated.getComment());
    }

    @Test
    void testUpdateSome_whenUpdateWithNonTourId_thenThrow() {
        assertThrows(NoSuchElementException.class,() ->
                tourRatingService.updateSome(TOUR_ID, CUSTOMER_ID, NEW_SCORE, NEW_COMMENT));
    }

    @Test
    void testDelete() {
        List<TourRating> tourRatings = tourRatingService.lookupAll();
        tourRatingService.delete(tourRatings.get(0).getTour().getId(), tourRatings.get(0).getCustomerId());
        int size = tourRatingService.lookupAll().size();

        assertEquals(tourRatings.size() - 1, size);
    }

    @Test
    void testDelete_whenDeleteNonExistingTour_thenThrow() {
        assertThrows(NoSuchElementException.class,
                () -> tourRatingService.delete(NOT_A_TOUR_ID, CUSTOMER_ID));
    }

    @Test
    void testRateMany() {
        tourRatingService.rateMany(TOUR_ID, SCORE, new Integer[]{CUSTOMER_ID});

        Page<TourRating> tourRatings = tourRatingService.lookupRatings(TOUR_ID, null);

        boolean allMatch = tourRatings
                .stream()
                .filter(tourRating -> tourRating.getCustomerId().equals(CUSTOMER_ID))
                .allMatch(tourRating -> tourRating.getScore().equals(SCORE));

        assertTrue(allMatch);
    }

    @Test
    void testRateMany_whenRateForRatedCustomerId_thenThrow() {
        assertThrows(DataIntegrityViolationException.class,
                () -> tourRatingService.rateMany(TOUR_ID, SCORE, new Integer[]{CUSTOMER_ID, CUSTOMER_ID}));
    }
}