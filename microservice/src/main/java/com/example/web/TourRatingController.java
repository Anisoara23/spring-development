package com.example.web;

import com.example.domain.TourRating;
import com.example.repo.TourRatingRepository;
import com.example.repo.TourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "tours/{tourId}/ratings")
public class TourRatingController {

    private final TourRatingRepository tourRatingRepository;

    private final TourRepository tourRepository;

    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourRating rateTour(@RequestBody @Validated TourRating ratingDto, @PathVariable("tourId") String tourId) {
        verifyTour(tourId);

        TourRating tourRating = new TourRating();
        tourRating.setTourId(tourId);
        tourRating.setCustomerId(ratingDto.getCustomerId());
        tourRating.setComment(ratingDto.getComment());
        tourRating.setScore(ratingDto.getScore());

        return tourRatingRepository.save(tourRating);
    }

    @GetMapping("average")
    public Map<String, Double> getAverageScore(@PathVariable("tourId") String tourId) {
        verifyTour(tourId);

        return Map.of("average", tourRatingRepository.findByTourId(tourId).stream()
                .mapToInt(TourRating::getScore)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings!")));
    }

    @GetMapping
    public List<TourRating> getTourRatings(@PathVariable("tourId") String tourId) {
        verifyTour(tourId);
        return tourRatingRepository.findByTourId(tourId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TourRating updateWithPut(@RequestBody @Validated TourRating ratingDto, @PathVariable("tourId") String tourId) {
        TourRating tourRating = verifyTourRating(tourId, ratingDto.getCustomerId());

        tourRating.setScore(ratingDto.getScore());
        tourRating.setComment(ratingDto.getComment());

        return tourRatingRepository.save(tourRating);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public TourRating updateWithPatch(@RequestBody @Validated TourRating ratingDto, @PathVariable("tourId") String tourId) {
        TourRating tourRating = verifyTourRating(tourId, ratingDto.getCustomerId());

        if (ratingDto.getScore() != null) {
            tourRating.setScore(ratingDto.getScore());
        }

        if (ratingDto.getComment() != null) {
            tourRating.setComment(ratingDto.getComment());
        }

        return tourRatingRepository.save(tourRating);
    }

    @DeleteMapping("{customerId}")
    public void deleteRating(@PathVariable("tourId") String tourId, @PathVariable("customerId") int customerId) {
        TourRating tourRating = verifyTourRating(tourId, customerId);

        tourRatingRepository.delete(tourRating);
    }

    private TourRating verifyTourRating(String tourId, int customerId) {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("Tour Rating pair for request(" +
                        "" + tourId + " for customer " + customerId + ")"));
    }

    @GetMapping("pageable")
    public Page<TourRating> getAllRatingsForTour(@PathVariable("tourId") String tourId,
                                                 Pageable pageable) {
        verifyTour(tourId);
        Page<TourRating> byPkTourId = tourRatingRepository.findByTourId(tourId, pageable);
        return new PageImpl<>(
                byPkTourId.get().collect(Collectors.toList()),
                pageable,
                byPkTourId.getTotalElements()
        );
    }

    private void verifyTour(String tourId) {
        if (!tourRepository.existsById(tourId)) {
            throw new NoSuchElementException("Tour does not exists " + tourId);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
