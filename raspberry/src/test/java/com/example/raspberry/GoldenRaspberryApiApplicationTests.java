package com.example.raspberry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GoldenRaspberryApiApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void shouldReturnMinAndMaxAwardIntervals() {
		ResponseEntity<Map> response = restTemplate.getForEntity("/producers/intervals", Map.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).containsKeys("min", "max");
	}
}
