package com.example.transport_marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    // Получение всех бронирований
    @GetMapping
    public ResponseEntity<List<Booking>> getBookings(){
        return new ResponseEntity<>(bookingService.getAllBookings(), HttpStatus.OK);
    }
    // Создание бронирования
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking){
        Booking newBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable int id){
        boolean removed = bookingService.cancelBooking(id);
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
