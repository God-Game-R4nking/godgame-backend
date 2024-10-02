package com.example.godgame.Image.teenieping.repository;


import com.example.godgame.Image.teenieping.entity.TeeniepingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeeniepingImageRepository extends JpaRepository<TeeniepingImage, Long> {
    @Query(value = "SELECT * FROM teenieping_image ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<TeeniepingImage> findRandomTeeinepingImages(@Param("count") int count);
}
