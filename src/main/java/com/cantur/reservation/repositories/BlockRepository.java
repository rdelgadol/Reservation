package com.cantur.reservation.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cantur.reservation.entities.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, LocalDate> {  
    //Returns all blocks between to dates.
    @Query("SELECT b FROM Block b WHERE b.startDate <= :endDate AND b.endDate >= :startDate ORDER BY b.startDate")
    List<Block> get(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
	//Check if there are overlapping blocks between two dates.
    @Query("SELECT COUNT(b) > 0 FROM Block b WHERE "
    		+ "b.startDate <= :endDate AND b.endDate >= :startDate")
    boolean concurrent(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
