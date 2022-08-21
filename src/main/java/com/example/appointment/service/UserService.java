package com.example.appointment.service;


import com.example.appointment.entity.User;
import com.example.appointment.exception.InvalidInputException;
import com.example.appointment.exception.ResourceUnAvailableException;
import com.example.appointment.provider.PasswordCryptographyProvider;
import com.example.appointment.repository.UserRepository;
import com.example.appointment.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	private PasswordCryptographyProvider passwordCryptographyProvider;


	public User register(User user) throws InvalidInputException {
		ValidationUtils.validate(user);

		user.setCreatedDate(LocalDate.now().toString());
		encryptPassword(user);
		userRepository.save(user);
		return user;
	}

	public User getUser(String id) {
		return Optional.ofNullable(userRepository.findById(id))
				.get()
				.orElseThrow(ResourceUnAvailableException::new);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	private void encryptPassword(final User newUser) {

		String password = newUser.getPassword();
		final String[] encryptedData = passwordCryptographyProvider.encrypt(password);
		newUser.setSalt(encryptedData[0]);
		newUser.setPassword(encryptedData[1]);
	}
}
