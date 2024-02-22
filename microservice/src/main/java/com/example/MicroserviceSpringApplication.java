package com.example;

import com.example.servie.TourPackageService;
import com.example.servie.TourService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class MicroserviceSpringApplication implements CommandLineRunner {

    @Value("${app.importedFile}")
    private String importedFile;

    @Autowired
    private TourService tourService;

    @Autowired
    private TourPackageService tourPackageService;

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        loadTourAtStartup();
    }

    private void loadTourAtStartup() throws IOException {
        createTourPackages();
        long numOfPackages = tourPackageService.total();

        createTours(importedFile);
        long numOfTours = tourService.total();
    }

    private void createTourPackages() {
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
    }

    private void createTours(String fileToImport) throws IOException {
        TourFromFile.read(fileToImport).forEach(tourFromFile ->
                tourService.createTour(tourFromFile.getTitle(),
                        tourFromFile.getPackageName(),
                        tourFromFile.getDetails()));
    }

    private static class TourFromFile {

        private String title;
        private String packageName;
        private Map<String, String> details;

        TourFromFile(Map<String, String> record) {
            this.title = record.get("title");
            this.packageName = record.get("packageType");
            this.details = record;
            this.details.remove("packageType");
            this.details.remove("title");
        }

        static List<TourFromFile> read(String fileToImport) throws IOException {
            List<Map<String, String>> records = new ObjectMapper()
                    .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                    .readValue(new FileInputStream(fileToImport),
                            new TypeReference<>() {
                            });

            return records.stream()
                    .map(TourFromFile::new)
                    .collect(Collectors.toList());
        }

        public String getTitle() {
            return title;
        }

        public String getPackageName() {
            return packageName;
        }

        public Map<String, String> getDetails() {
            return details;
        }
    }

}