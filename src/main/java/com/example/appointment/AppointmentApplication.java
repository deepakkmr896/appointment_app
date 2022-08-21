

package com.example.appointment;

import com.example.appointment.config.ApiConfiguration;
import com.example.appointment.config.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApiConfiguration.class, WebConfiguration.class})
public class AppointmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppointmentApplication.class, args);
	}

}
