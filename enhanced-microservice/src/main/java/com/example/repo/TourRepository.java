package com.example.repo;

import com.example.domain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Integer> {

    /**
     * Lookup a Page of Tours associated with a TourPackage
     *
     * @param code     the tour Package code.
     * @param pageable details for finding the correct page.
     * @return A page of tours if found, empty otherwise.
     */
    Page<Tour> findByTourPackageCode(@Param("code") String code, Pageable pageable);

    //Not exposed by Spring Data REST
    @Override
    @RestResource(exported = false)
    <S extends Tour> S save(S s);

    //Not exposed by Spring Data REST
    @Override
    @RestResource(exported = false)
    <S extends Tour> List<S> saveAll(Iterable<S> iterable);

    //Not exposed by Spring Data REST
    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer);

    //Not exposed by Spring Data REST
    @Override
    @RestResource(exported = false)
    void delete(Tour tour);

    //Not exposed by Spring Data REST
    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> iterable);

    //Not exposed by Spring Data REST
    @Override
    @RestResource(exported = false)
    void deleteAll();
}
