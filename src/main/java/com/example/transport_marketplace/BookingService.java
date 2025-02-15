package com.example.transport_marketplace;

import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
@Service
public class BookingService {
    private final List<Booking> bookings = new ArrayList<>();
    private int nextId = 1;
    public List<Booking> getAllBookings(){
        return bookings;
    }
    public Booking createBooking(Booking booking){
        booking.setId(nextId++);
        bookings.add(booking);
        return  booking;
    }

    public boolean cancelBooking(int id){
        return bookings.removeIf(b -> b.getId() == id);
    }
}
