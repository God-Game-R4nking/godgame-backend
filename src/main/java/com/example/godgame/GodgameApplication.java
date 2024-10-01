package com.example.godgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.godgame.member", "com.example.godgame.board", "com.example.godgame.comment"})
@EnableMongoRepositories(basePackages = {"com.example.godgame.chat", "com.example.godgame.counter", "com.example.godgame.gameroom"})
public class GodgameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GodgameApplication.class, args);
	}

}
