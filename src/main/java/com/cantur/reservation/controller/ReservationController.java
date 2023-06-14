package com.cantur.reservation.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cantur.reservation.entities.Block;
import com.cantur.reservation.entities.Booking;
import com.cantur.reservation.services.BookingService;
import com.cantur.reservation.services.BlockService;

@RestController
public class ReservationController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private BlockService blockService;

	//Returns bookings between two dates
	@GetMapping("/booking")
	public ResponseEntity<List<Booking>> bookingGet(@RequestParam("startDate") LocalDate startDate, 
			@RequestParam("endDate") LocalDate endDate) {
		return ResponseEntity.ok(bookingService.get(startDate, endDate));
	}
	
	@PostMapping("/booking")
	public ResponseEntity<Booking> bookingPost(@RequestBody Booking booking) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.post(booking));
	}

	@PutMapping("/booking")
	public ResponseEntity<Booking> bookingPut(@RequestBody Booking booking) {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.put(booking));
	}

	@DeleteMapping("/booking/{startDate}")
	public ResponseEntity<?> bookingDelete(@PathVariable LocalDate startDate) {
		bookingService.delete(startDate);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/booking/available")
	public ResponseEntity<Booking> bookingPutAvailable(@RequestParam("startDate") LocalDate startDate,
			@RequestParam("available") Boolean available) {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.putAvailable(startDate, available));
	}

	//Returns blocks between two dates.
	@GetMapping("/block")
	public ResponseEntity<List<Block>> blockGet(@RequestParam("startDate") LocalDate startDate, 
			@RequestParam("endDate") LocalDate endDate) {
		return ResponseEntity.ok(blockService.get(startDate, endDate));
	}
	
	@PostMapping("/block")
	public ResponseEntity<Block> blockPost(@RequestBody Block block) {
		return ResponseEntity.status(HttpStatus.CREATED).body(blockService.post(block));
	}

	@PutMapping("/block")
	public ResponseEntity<Block> blockPut(@RequestBody Block block) {
		return ResponseEntity.status(HttpStatus.OK).body(blockService.put(block));
	}

	@DeleteMapping("/block/{startDate}")
	public ResponseEntity<?> blockDelete(@PathVariable LocalDate startDate) {
		blockService.delete(startDate);
		return ResponseEntity.noContent().build();
	}
}
