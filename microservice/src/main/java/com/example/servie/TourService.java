package com.example.servie;

import com.example.domain.Tour;
import com.example.domain.TourPackage;
import com.example.repo.TourPackageRepository;
import com.example.repo.TourRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TourService {

    private final TourRepository tourRepository;

    private final TourPackageRepository tourPackageRepository;

    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(String title,
                           String tourPackageName,
                           Map<String, String> details) {
        TourPackage fetchedTourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour package does not exists"));

        return tourRepository.save(new Tour(title, fetchedTourPackage, details));
    }

    public long total() {
        return tourRepository.count();
    }
}
