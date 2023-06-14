package com.cantur.reservation.entities;

import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Booking {
	@Id
	private LocalDate startDate;
	private LocalDate endDate;
	//Name of the customer.
	private String name;
	private Boolean available;
}
