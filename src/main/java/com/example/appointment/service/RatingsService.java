package com.example.appointment.service;

import com.example.appointment.entity.Doctor;
import com.example.appointment.entity.Rating;
import com.example.appointment.repository.DoctorRepository;
import com.example.appointment.repository.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RatingsService {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private RatingsRepository ratingsRepository;

	@Autowired
	private DoctorRepository doctorRepository;
	
	public void submitRatings(Rating rating) {
		try {
			rating.setId(UUID.randomUUID().toString());
			ratingsRepository.save(rating);
			String doctorId = rating.getDoctorId();
			if (doctorId != null) {

				Optional<Doctor> doctor = doctorRepository.findById(doctorId);

				if (doctor.isPresent()) {
					DecimalFormat df = new DecimalFormat("0.00");
					List<Integer> totalRatingsData = ratingsRepository.findByDoctorId(doctorId)
							.stream().map(rt -> rt.getRating())
							.collect(Collectors.toList());
					Integer totalRatingSum = totalRatingsData.stream().reduce(0, (a, b) -> a + b);
					Integer totalRatingAttempt = totalRatingsData.size();

					Double averageRating = (double) totalRatingSum / (double) totalRatingAttempt;
					averageRating = Double.valueOf(df.format(averageRating));
					Doctor doctorData = doctor.get();
					doctorData.setRating(averageRating);

					doctorRepository.save(doctorData);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
