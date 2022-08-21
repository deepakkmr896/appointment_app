package com.example.appointment.controller;

import com.example.appointment.exception.InvalidInputException;
import com.example.appointment.service.UserService;
import com.example.appointment.entity.Appointment;
import com.example.appointment.entity.User;
import com.example.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserAdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppointmentService appointmentService;


	@GetMapping(path = "/{id}")
	public ResponseEntity<User> getUser(@RequestHeader("authorization") String accessToken,
	                                    @PathVariable("id") final String userUuid) {
		final User User = userService.getUser(userUuid);
		return ResponseEntity.ok(User);
	}

	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody User user) throws InvalidInputException {
		return ResponseEntity.ok(userService.register(user));
	}

	@GetMapping("/{userId}/appointments")
	public ResponseEntity<List<Appointment>> getAppointmentForUser(@PathVariable("userId") String userId) {
		return ResponseEntity.ok(appointmentService.getAppointmentsForUser(userId));
	}
}
