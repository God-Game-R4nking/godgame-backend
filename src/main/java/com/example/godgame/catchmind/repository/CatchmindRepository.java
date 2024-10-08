package com.example.godgame.catchmind.repository;

import com.example.godgame.Image.country.entity.CountryImage;
import com.example.godgame.catchmind.entity.Catchmind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CatchmindRepository extends JpaRepository<Catchmind, Long> {

    @Query(value = "SELECT * FROM catchmind ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Catchmind> findRandomCatchminds(@Param("count") int count);
}