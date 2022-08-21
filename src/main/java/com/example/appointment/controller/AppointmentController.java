package com.example.appointment.controller;


import com.example.appointment.exception.InvalidInputException;
import com.example.appointment.exception.SlotUnavailableException;
import com.example.appointment.entity.Appointment;
import com.example.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@PostMapping
	public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) throws SlotUnavailableException, InvalidInputException {
		return ResponseEntity.ok(appointmentService.appointment(appointment));
	}

	@GetMapping("/{appointmentId}")
	public ResponseEntity<Appointment> getAppointment(@PathVariable String appointmentId) {
		return ResponseEntity.ok(appointmentService.getAppointment(appointmentId));
	}
}