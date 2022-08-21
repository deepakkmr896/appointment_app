package com.example.appointment.repository;

import com.example.appointment.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	@Override
	List<User> findAll();

	User findByEmailId(String emailId);
}
