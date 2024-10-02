package com.example.godgame.Image.country.repository;

import com.example.godgame.Image.country.entity.CountryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CountryImageRepository extends JpaRepository<CountryImage, Long> {

    @Query(value = "SELECT * FROM country_image ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<CountryImage> findRandomCountryImages(@Param("count") int count);
}