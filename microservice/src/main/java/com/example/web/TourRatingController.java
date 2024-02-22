package com.example.web;

import com.example.domain.Tour;
import com.example.domain.TourRating;
import com.example.domain.TourRatingPk;
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
    public RatingDto rateTour(@RequestBody @Validated RatingDto ratingDto, @PathVariable("tourId") int tourId) {
        Tour tour = verifyTour(tourId);
        TourRatingPk pk = new TourRatingPk();
        pk.setTour(tour);
        pk.setCustomerId(ratingDto.getCustomerId());

        TourRating tourRating = new TourRating();
        tourRating.setPk(pk);
        tourRating.setComment(ratingDto.getComment());
        tourRating.setScore(ratingDto.getScore());

        TourRating saved = tourRatingRepository.save(tourRating);

        return new RatingDto(tourRating);
    }

    @GetMapping("average")
    public Map<String, Double> getAverageScore(@PathVariable("tourId") int tourId) {
        verifyTour(tourId);

        return Map.of("average", tourRatingRepository.findByPkTourId(tourId).stream()
                .mapToInt(TourRating::getScore)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings!")));
    }

    @GetMapping
    public List<RatingDto> getTourRatings(@PathVariable("tourId") int tourId) {
        verifyTour(tourId);
        return tourRatingRepository.findByPkTourId(tourId)
                .stream()
                .map(RatingDto::new)
                .collect(Collectors.toList());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public RatingDto updateWithPut(@RequestBody @Validated RatingDto ratingDto, @PathVariable("tourId") int tourId) {
        TourRating tourRating = verifyTourRating(tourId, ratingDto.getCustomerId());

        tourRating.setScore(ratingDto.getScore());
        tourRating.setComment(ratingDto.getComment());

        TourRating saved = tourRatingRepository.save(tourRating);
        return new RatingDto(saved);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public RatingDto updateWithPatch(@RequestBody @Validated RatingDto ratingDto, @PathVariable("tourId") int tourId) {
        TourRating tourRating = verifyTourRating(tourId, ratingDto.getCustomerId());

        if (ratingDto.getScore() != null) {
            tourRating.setScore(ratingDto.getScore());
        }

        if (ratingDto.getComment() != null) {
            tourRating.setComment(ratingDto.getComment());
        }

        TourRating saved = tourRatingRepository.save(tourRating);
        return new RatingDto(saved);
    }

    @DeleteMapping("{customerId}")
    public void deleteRating(@PathVariable("tourId") int tourId, @PathVariable("customerId") int customerId) {
        TourRating tourRating = verifyTourRating(tourId, customerId);

        tourRatingRepository.delete(tourRating);
    }

    private TourRating verifyTourRating(int tourId, int customerId) {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("Tour Rating pair for request(" +
                        "" + tourId + " for customer " + customerId + ")"));
    }

    @GetMapping("pageable")
    public Page<RatingDto> getAllRatingsForTour(@PathVariable("tourId") int tourId,
                                                Pageable pageable) {
        verifyTour(tourId);
        Page<TourRating> byPkTourId = tourRatingRepository.findByPkTourId(tourId, pageable);
        return new PageImpl<>(
                byPkTourId.get().map(RatingDto::new)
                        .collect(Collectors.toList()),
                pageable,
                byPkTourId.getTotalElements()
        );
    }

    private Tour verifyTour(int tourId) {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exists " + tourId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
