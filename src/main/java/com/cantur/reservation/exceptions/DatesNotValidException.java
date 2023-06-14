package com.cantur.reservation.exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DatesNotValidException extends ReservationException  {
	
	private static final long serialVersionUID = 43876691117560211L;	
	
	public DatesNotValidException(LocalDate startDate, LocalDate endtDate) {
		super("Incorrect dates. "+startDate.toString()+" is after "+endtDate.toString());
	}
	public DatesNotValidException(String action, LocalDate startDate, LocalDate endtDate) {
		super("It´s not possible to "+action+" in these dates. "+startDate.toString()+" is after "+
				endtDate.toString());
	}
}
