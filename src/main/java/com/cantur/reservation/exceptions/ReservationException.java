package com.cantur.reservation.exceptions;

public class ReservationException extends RuntimeException {
	
	private static final long serialVersionUID = 43876691117560211L;
	
	public ReservationException(String message) {
		super(message);
		System.out.println(message);
	}
}
