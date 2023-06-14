package com.cantur.reservation.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Class that returns a java exception in JSON format.
 */
@Getter
@RequiredArgsConstructor
public class ResponseError {

	@NonNull
	private HttpStatusCode status;
	@NonNull
	private String exception;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime date_error = LocalDateTime.now();
	@NonNull
	private String message;
}
