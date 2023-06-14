package com.cantur.reservation.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cantur.reservation.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, LocalDate> {    
    //Returns all bookings between two dates.
    @Query("SELECT b FROM Booking b WHERE b.startDate <= :endDate AND b.endDate >= :startDate ORDER BY b.startDate")
    List<Booking> get(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    

	//Check if there are available bookings between two dates, without considering itself.
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.startDate <> :startDate AND b.available = true AND "
    		+ "(b.startDate <= :endDate AND b.endDate >= :startDate)")
    boolean concurrent(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
