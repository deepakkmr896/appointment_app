package com.example.appointment.controller;

import com.example.appointment.service.RatingsService;
import com.example.appointment.entity.Rating;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
@Log4j2
public class RatingsController {

	@Autowired
	private RatingsService ratingsService;

	@PostMapping
	public ResponseEntity submitRatings(@RequestBody Rating rating) {
		try {
			ratingsService.submitRatings(rating);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			log.error("Error occurred while submitting rating, error : {}", e);
			return ResponseEntity.internalServerError().build();
		}
	}
}
