package com.example.web;

import com.example.domain.TourRating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RatingDto {

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Integer customerId;

    public RatingDto() {
    }

    public RatingDto(TourRating tourRating) {
        comment = tourRating.getComment();
        score = tourRating.getScore();
        customerId = tourRating.getPk().getCustomerId();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
