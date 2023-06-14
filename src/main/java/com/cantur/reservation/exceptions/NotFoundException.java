package com.cantur.reservation.exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ReservationException {
	
	private static final long serialVersionUID = 43876691117560211L;
	
	public NotFoundException(String entity, LocalDate startDate) {
		super(entity+" starting on "+startDate.toString()+" not found.");
	}
}
