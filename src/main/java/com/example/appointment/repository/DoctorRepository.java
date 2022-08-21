package com.example.appointment.repository;

import com.example.appointment.entity.Doctor;
import com.example.appointment.enums.Speciality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, String> {
	List<Doctor> findBySpecialityOrderByRatingDesc(Speciality speciality);

	List<Doctor> findAllByOrderByRatingDesc();
}
