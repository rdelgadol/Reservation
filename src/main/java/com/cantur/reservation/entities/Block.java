package com.cantur.reservation.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Block {
	@Id
	private LocalDate startDate;
	private LocalDate endDate;
	//Why these days are blocked.
	private String cause;
}
