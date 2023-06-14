package com.cantur.reservation.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cantur.reservation.repositories.BlockRepository;
import com.cantur.reservation.repositories.BookingRepository;
import com.cantur.reservation.exceptions.NotFoundException;
import com.cantur.reservation.entities.Booking;
import com.cantur.reservation.exceptions.ConcurrentException;
import com.cantur.reservation.exceptions.DatesNotValidException;

@Service
public class BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private BlockRepository blockRepository;
		   
    //Validate that there aren’t available bookings or blocks between the parameter’s dates.
	private synchronized Booking concurrentValidate(Booking booking)
	{
		if(bookingRepository.concurrent(booking.getStartDate(),booking.getEndDate()) ||
				blockRepository.concurrent(booking.getStartDate(),booking.getEndDate()))
			throw new ConcurrentException(booking.getStartDate(),booking.getEndDate());
		else return bookingRepository.save(booking);
	}
	//Validation required when adding or updating a booking, but not when activating it.
	private Booking validate(Booking booking)
	{
		if(booking.getStartDate().isAfter(booking.getEndDate())) 
			throw new DatesNotValidException("book", booking.getStartDate(),booking.getEndDate());
		else {
			if(booking.getAvailable().booleanValue()) return concurrentValidate(booking);
			else return bookingRepository.save(booking);
		}
	}
	   
    //Returns all bookings between to dates.
	public List<Booking> get(LocalDate startDate,LocalDate endDate) {
		if(startDate.isAfter(endDate)) throw new DatesNotValidException(startDate, endDate);
		else return bookingRepository.get(startDate, endDate);
	}
	
	/*As an insert, the booking is saved only if there is not anyone with the same primary key (start date).
	 I don´t synchronize this method, because it would affect performance and there is no risk of 
	 overlapping bookings.*/
	public Booking post(Booking booking) {
		Optional<Booking> optBooking= bookingRepository.findById(booking.getStartDate());
		if(optBooking.isPresent()) throw new ConcurrentException(booking.getStartDate(),booking.getEndDate());
		else return validate(booking);
	}
	
	/*As an update, the booking is saved only if there is an existing one with the same primary key (start date).
	 I don´t synchronize this method, because it would affect performance and there is no risk of 
	 overlapping bookings.*/
	public Booking put(Booking booking) {
		return bookingRepository.findById(booking.getStartDate()).map(p -> {
			return validate(booking);
		}).orElseThrow(() -> new NotFoundException("Booking", booking.getStartDate()));
	}

	public void delete(LocalDate startDate) {
		Booking booking = bookingRepository.findById(startDate)
				.orElseThrow(() -> new NotFoundException("Booking", startDate));
		bookingRepository.delete(booking);
	}
	
	/*To cancel bookings if available=false or making them available again if available=true. Cancelled bookings 
	 are not considered in overlapping validations.*/
	public Booking putAvailable(LocalDate startDate,Boolean available) {
		return bookingRepository.findById(startDate).map(p -> {
			p.setAvailable(available);
			/*On activating the booking, it is checked if there are concurrent bookings or books, otherwise 
			there´s no need.*/
			if(available.booleanValue()) return concurrentValidate(p);
			else return bookingRepository.save(p);
		}).orElseThrow(() -> new NotFoundException("Booking", startDate));
	}
}
