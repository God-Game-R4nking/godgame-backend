package com.example.godgame.counter.service;

import com.example.godgame.counter.entity.Counter;
import com.example.godgame.counter.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    @Autowired
    private CounterRepository counterRepository;

    public long getNextSequence(String seqName) {
        Counter counter = counterRepository.findById(seqName).orElse(new Counter(seqName, 0));
        counter.setSeq(counter.getSeq() + 1);
        counterRepository.save(counter);
        return counter.getSeq();
    }
}