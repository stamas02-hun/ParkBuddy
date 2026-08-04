package org.example.parkbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ParkBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkBuddyApplication.class, args);
	}

}
