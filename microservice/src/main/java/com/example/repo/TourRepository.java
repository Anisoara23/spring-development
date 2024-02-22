package com.example.repo;

import com.example.domain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface TourRepository extends CrudRepository<Tour, String>, PagingAndSortingRepository<Tour, String> {

    Page<Tour> findByTourPackageCode(@Param("code") String code, Pageable pageable);

    @RestResource(exported = false)
    @Override
    <S extends Tour> S save(S entity);

    @Query(value = "{'tourPackageCode': ?0}",
            fields = "{'id':1, 'title':1, 'tourPackageCode':1, 'tourPackageName':1}")
    Page<Tour> findSummaryByTourPackageCode(@Param("code") String code, Pageable pageable);
}
