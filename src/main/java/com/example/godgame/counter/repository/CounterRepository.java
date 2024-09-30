package com.example.godgame.counter.repository;

import com.example.godgame.counter.entity.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Counter, String> {
}
