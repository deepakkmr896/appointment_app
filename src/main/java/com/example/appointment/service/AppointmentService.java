package com.example.appointment.service;

import com.example.appointment.exception.InvalidInputException;
import com.example.appointment.exception.ResourceUnAvailableException;
import com.example.appointment.exception.SlotUnavailableException;
import com.example.appointment.repository.AppointmentRepository;
import com.example.appointment.repository.UserRepository;
import com.example.appointment.util.ValidationUtils;
import com.example.appointment.entity.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.appointment.util.ValidationUtils.validate;

@Service
@RequiredArgsConstructor
public class AppointmentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public String appointment(Appointment appointment) throws SlotUnavailableException, InvalidInputException {
		ValidationUtils.validate(appointment);

		Appointment existAppointment = appointmentRepository.findByDoctorIdAndTimeSlotAndAppointmentDate(appointment.getDoctorId(),
				appointment.getTimeSlot(), appointment.getAppointmentDate());

		if (existAppointment == null) {
			appointmentRepository.save(appointment);
			return appointment.getAppointmentId();
		}

		throw new SlotUnavailableException();
	}

	public Appointment getAppointment(String appointmentId) {
		return Optional.ofNullable(appointmentRepository.findById(appointmentId))
				.get()
				.orElseThrow(ResourceUnAvailableException::new);
	}

	public List<Appointment> getAppointmentsForUser(String userId) {
		return appointmentRepository.findByUserId(userId);
	}
}
