package com.example.service;

import com.example.domain.Tour;
import com.example.domain.TourRating;
import com.example.repo.TourRatingRepository;
import com.example.repo.TourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.service.utils.TestUtils.CUSTOMER_ID;
import static com.example.service.utils.TestUtils.COMMENT;
import static com.example.service.utils.TestUtils.SCORE;
import static com.example.service.utils.TestUtils.TOUR_ID;
import static com.example.service.utils.TestUtils.TOUR_RATING_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TourRatingServiceTest {
    @Mock
    private TourRatingRepository tourRatingRepository;
    @Mock
    private TourRepository tourRepository;

    @InjectMocks
    private TourRatingService tourRatingService;

    @Mock
    private Tour tourMock;
    @Mock
    private TourRating tourRatingMock;

    @Captor
    private ArgumentCaptor<TourRating> ratingArgumentCaptor;

    @BeforeEach
    void setUp() {
        lenient().when(tourRepository.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        lenient().when(tourMock.getId()).thenReturn(TOUR_ID);
        lenient().when(tourRatingRepository.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID)).thenReturn(Optional.of(tourRatingMock));
        lenient().when(tourRatingRepository.findByTourId(TOUR_ID)).thenReturn(List.of(tourRatingMock));
    }

    @Test
    void testCreateNewRating() {
        when(tourRatingRepository.save(any())).thenReturn(tourRatingMock);

        assertDoesNotThrow(() ->
                tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT));

        verify(tourRatingRepository).save(ratingArgumentCaptor.capture());

        assertEquals(tourMock, ratingArgumentCaptor.getValue().getTour());
        assertEquals(CUSTOMER_ID, ratingArgumentCaptor.getValue().getCustomerId());
        assertEquals(SCORE, ratingArgumentCaptor.getValue().getScore());
        assertEquals(COMMENT, ratingArgumentCaptor.getValue().getComment());
    }

    @Test
    void testLookupRatingById_whenProvideValidId() {
        when(tourRatingRepository.findById(anyInt())).thenReturn(Optional.of(tourRatingMock));
        when(tourRatingMock.getId()).thenReturn(TOUR_RATING_ID);

        Optional<TourRating> tourRating = tourRatingService.lookupRatingById(TOUR_RATING_ID);

        assertEquals(TOUR_RATING_ID, tourRating.get().getId());
    }

    @Test
    void testLookupRatingById_whenProvideNonValidId() {
        when(tourRatingRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<TourRating> tourRating = tourRatingService.lookupRatingById(TOUR_RATING_ID);

        assertTrue(tourRating.isEmpty());
    }

    @Test
    void testLookupAll() {
        when(tourRatingRepository.findAll()).thenReturn(List.of(tourRatingMock));

        List<TourRating> tourRatings = tourRatingService.lookupAll();

        assertTrue(tourRatings.contains(tourRatingMock));
    }

    @Test
    void testLookupRatings() {
        Pageable pageable = mock(Pageable.class);
        Page page = mock(Page.class);

        when(tourRatingRepository.findByTourId(anyInt(), any()))
                .thenReturn(page);

        Page<TourRating> tourRatings = tourRatingService.lookupRatings(TOUR_ID, pageable);

        assertEquals(tourRatings, page);
    }

    @Test
    void testDelete() {
        doNothing().when(tourRatingRepository).delete(tourRatingMock);

        assertDoesNotThrow(() -> tourRatingService.delete(TOUR_ID, CUSTOMER_ID));
        verify(tourRatingRepository).delete(any(TourRating.class));
    }

    @Test
    void testGetAverageScore() {
        when(tourRatingMock.getScore()).thenReturn(SCORE);

        Double averageScore = tourRatingService.getAverageScore(TOUR_ID);

        assertEquals(averageScore, SCORE);
    }

    @Test
    void testRateMany() {
        tourRatingService.rateMany(TOUR_ID, SCORE, new Integer[]{CUSTOMER_ID, CUSTOMER_ID});

        verify(tourRatingRepository, times(2)).save(any(TourRating.class));
    }

    @Test
    void testUpdate() {
        tourRatingService.update(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);

        verify(tourRatingRepository).save(any(TourRating.class));

        verify(tourRatingMock).setScore(SCORE);
        verify(tourRatingMock).setComment(COMMENT);
    }

    @Test
    void testUpdateSome() {
        tourRatingService.updateSome(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);

        verify(tourRatingRepository).save(any(TourRating.class));

        verify(tourRatingMock).setScore(SCORE);
        verify(tourRatingMock).setComment(COMMENT);
    }
}