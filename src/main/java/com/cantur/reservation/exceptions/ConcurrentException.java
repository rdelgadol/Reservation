package com.cantur.reservation.exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConcurrentException extends ReservationException {
	
	private static final long serialVersionUID = 43876691117560211L;
	
	public ConcurrentException(String action, LocalDate startDate, LocalDate endtDate) {
		super("It´s not possible to "+action+" from "+startDate.toString()+" to "+endtDate.toString()
			+". There is a concurrent booking or block.");
	}
	
	public ConcurrentException(LocalDate startDate, LocalDate endtDate) {
		this("book", startDate, endtDate);
	}
}
