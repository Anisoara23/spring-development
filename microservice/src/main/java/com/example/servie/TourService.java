package com.example.servie;

import com.example.domain.Difficulty;
import com.example.domain.Region;
import com.example.domain.Tour;
import com.example.domain.TourPackage;
import com.example.repo.TourPackageRepository;
import com.example.repo.TourRepository;
import org.springframework.stereotype.Service;

@Service
public class TourService {

    private final TourRepository tourRepository;

    private final TourPackageRepository tourPackageRepository;

    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(String title,
                           String description,
                           String blurb,
                           Integer price,
                           String duration,
                           String bullets,
                           String keywords,
                           String tourPackageName,
                           Difficulty difficulty,
                           Region region) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour package does not exists"));

        return tourRepository.save(new Tour(title, description, blurb,
                price, duration, bullets, keywords,
                tourPackage, difficulty, region));
    }

    public long total() {
        return tourRepository.count();
    }
}
