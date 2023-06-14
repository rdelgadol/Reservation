package com.cantur.reservation.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cantur.reservation.repositories.BlockRepository;
import com.cantur.reservation.repositories.BookingRepository;
import com.cantur.reservation.entities.Block;
import com.cantur.reservation.entities.Booking;
import com.cantur.reservation.exceptions.NotFoundException;
import com.cantur.reservation.exceptions.DatesNotValidException;
import com.cantur.reservation.exceptions.ConcurrentException;

@Service
public class BlockService {

	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private BlockRepository blockRepository;
  
	//Validation required when adding or updating a block.
	private Block validate(Block block)
	{
		if(block.getStartDate().isAfter(block.getEndDate())) 
			throw new DatesNotValidException("block", block.getStartDate(),block.getEndDate());
		else return blockRepository.save(block);
	}
	
	/*If a block is established between two dates, this method is in charge of disabling all the bookings between
	 these two dates*/
	private void disableBookings(LocalDate startDate, LocalDate endDate) {
		List<Booking> bookings = bookingRepository.get(startDate, endDate);
		for(Booking booking : bookings) {
			if(booking.getAvailable() == null || booking.getAvailable().booleanValue()) {
				booking.setAvailable(false);
				bookingRepository.save(booking);
			}
		}
	}
	private void disableBookings(Block block) {
		disableBookings(block.getStartDate(), block.getEndDate()); 
	}
	 
    //Returns all blocks between to dates.
	public List<Block> get(LocalDate startDate,LocalDate endDate) {
		if(startDate.isAfter(endDate)) throw new DatesNotValidException(startDate, endDate);
		else return blockRepository.get(startDate, endDate);
	}
	
	/*As an insert, the block is saved only if there is not anyone with the same primary key (start date).
	 I don´t synchronize this method, because it would affect performance and there is no risk of 
	 getting blocks with the same id.*/
	public Block post(Block block) {
		Optional<Block> optBlock= blockRepository.findById(block.getStartDate());
		if(optBlock.isPresent()) 
			throw new ConcurrentException("block", block.getStartDate(),block.getEndDate());
		else {
			//Disable all the bookings between the block’s dates
			disableBookings(block);
			return validate(block);
		}
	}
	
	/*As an update, the block is saved only if there is an existing one with the same primary key (start date).
	 I don´t synchronize this method, because it would affect performance there is no risk of 
	 getting blocks with the same id.*/
	public Block put(Block block) {
		return blockRepository.findById(block.getStartDate()).map(p -> {
			//Disable all the bookings between the block’s dates
			disableBookings(block);
			return validate(block);
		}).orElseThrow(() -> new NotFoundException("Block", block.getStartDate()));
	}

	public void delete(LocalDate startDate) {
		Block block = blockRepository.findById(startDate)
				.orElseThrow(() -> new NotFoundException("Block", startDate));
		blockRepository.delete(block);
	}
}
