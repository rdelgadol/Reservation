package com.cantur.reservation.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cantur.reservation.exceptions.ReservationException;

/**
 * Class that handles java exceptions and returns JSON elements with each error information.
 */
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
	
	/**Method to obtain the simple name of an exception.
	 * @param ex Exception that is thrown at a certain moment.
	 * @return Just the exception’s name, without including the path.
	 */
	private String getName(Exception ex) {
		String[] path=ex.getClass().getName().split("\\.");
		return path[path.length-1];
	}
	
	@ExceptionHandler(ReservationException.class)
	public ResponseEntity<ResponseError> handleFaltaParametro(ReservationException ex) {
		ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, getName(ex), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		ResponseError responseError = new ResponseError(status, getName(ex), ex.getMessage());
		return ResponseEntity.status(status).headers(headers).body(responseError);
	}
}
