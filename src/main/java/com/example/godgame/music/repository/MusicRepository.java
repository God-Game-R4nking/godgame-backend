package com.example.godgame.music.repository;

import com.example.godgame.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

    @Query(value = "SELECT * FROM music m WHERE m.music.era = :era ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Music> findRandomMusics(@Param("count") int count, @Param("era") String era);
}