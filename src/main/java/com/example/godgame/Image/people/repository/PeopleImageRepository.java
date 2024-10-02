package com.example.godgame.Image.people.repository;


import com.example.godgame.Image.people.entity.PeopleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleImageRepository extends JpaRepository<PeopleImage, Long> {
    @Query(value = "SELECT * FROM people_image ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<PeopleImage> findRandomPeopleImages(@Param("count") int count);
}
