package com.example.appointment.service;

import com.example.appointment.entity.Address;
import com.example.appointment.entity.Doctor;
import com.example.appointment.enums.Speciality;
import com.example.appointment.exception.InvalidInputException;
import com.example.appointment.exception.ResourceUnAvailableException;
import com.example.appointment.model.TimeSlot;
import com.example.appointment.repository.AddressRepository;
import com.example.appointment.repository.AppointmentRepository;
import com.example.appointment.repository.DoctorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.appointment.util.ValidationUtils.validate;

@Log4j2
@Service
public class DoctorService {
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private AddressRepository addressRepository;

	public Doctor register(Doctor doctor) throws InvalidInputException {
		validate(doctor);

		if (doctor.getAddress() == null) {
			List<String> err = new ArrayList<>();
			err.add("Address");
			throw new InvalidInputException(err);
		}

		try {
			doctor.setId(UUID.randomUUID().toString());
			if (doctor.getSpeciality() == null) {
				doctor.setSpeciality(Speciality.GENERAL_PHYSICIAN);
			}

			Address address = doctor.getAddress();
			Address addressRes = addressRepository.save(address);

			doctor.setAddress(addressRes);
			doctorRepository.save(doctor);
		} catch (Exception e) {
			log.error("Error occurred on saving the data to address or doctor entity, error : {}", e);
		}

		return doctor;
	}

	public Doctor getDoctor(String id) {
		if (id != null) {
			Optional<Doctor> doctor = doctorRepository.findById(id);
			if (doctor.isPresent()) {
				return doctor.get();
			}
		}
		throw new ResourceUnAvailableException();
	}

	public List<Doctor> getAllDoctorsWithFilters(String speciality) {

		if (speciality != null && !speciality.isEmpty()) {
			return doctorRepository.findBySpecialityOrderByRatingDesc(Speciality.valueOf(speciality));
		}
		return getActiveDoctorsSortedByRating();
	}

	@Cacheable(value = "doctorListByRating")
	private List<Doctor> getActiveDoctorsSortedByRating() {
		log.info("Fetching doctor list from the database");
		return doctorRepository.findAllByOrderByRatingDesc()
				.stream()
				.limit(20)
				.collect(Collectors.toList());
	}

	public TimeSlot getTimeSlots(String doctorId, String date) {

		TimeSlot timeSlot = new TimeSlot(doctorId, date);
		timeSlot.setTimeSlot(timeSlot.getTimeSlot()
				.stream()
				.filter(slot -> {
					return appointmentRepository
							.findByDoctorIdAndTimeSlotAndAppointmentDate(timeSlot.getDoctorId(), slot, timeSlot.getAvailableDate()) == null;

				})
				.collect(Collectors.toList()));

		return timeSlot;

	}
}
