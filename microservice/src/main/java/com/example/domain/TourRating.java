package com.example.domain;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class TourRating {

    @Id
    private String id;

    private String tourId;

    @NotNull
    private Integer customerId;

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "TourRating{" +
                "id='" + id + '\'' +
                ", tourId='" + tourId + '\'' +
                ", customerId=" + customerId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourRating that = (TourRating) o;
        return Objects.equals(id, that.id) && Objects.equals(tourId, that.tourId) && Objects.equals(customerId, that.customerId) && Objects.equals(score, that.score) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tourId, customerId, score, comment);
    }
}
