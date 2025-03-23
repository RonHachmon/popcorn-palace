package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.BookingDTO;
import com.att.tdp.popcorn_palace.entitys.Booking;
import com.att.tdp.popcorn_palace.entitys.Showtime;
import com.att.tdp.popcorn_palace.entitys.Users;
import com.att.tdp.popcorn_palace.exception.SeatAlreadyBookedException;
import com.att.tdp.popcorn_palace.exception.SeatOutOfRangeException;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    private final UsersRepository usersRepository;
    private final ShowtimeService showtimeService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UsersRepository usersRepository, ShowtimeService showtimeService) {
        this.bookingRepository = bookingRepository;
        this.usersRepository = usersRepository;
        this.showtimeService = showtimeService;
    }

    public Booking createBooking(BookingDTO bookingDTO) {
        Showtime showtime = showtimeService.getShowTimeById(bookingDTO.getShowtimeId());

        Users user = usersRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with ID: " + bookingDTO.getUserId()
                ));

        int totalSeats = showtime.getTheater().getTotalSits();
        Integer seatNumber = bookingDTO.getSeatNumber();

        if (seatNumber > totalSeats) {
            throw new SeatOutOfRangeException(
                    "Invalid seat number: " + seatNumber + ". The theater has " + totalSeats + " seats."
            );
        }

        if (isSeatTaken(seatNumber, bookingDTO.getShowtimeId())) {
            throw new SeatAlreadyBookedException(
                    "Seat number " + seatNumber + " is already booked."
            );
        }
        Booking booking = new Booking();
        booking.setSeatNumber(bookingDTO.getSeatNumber());
        booking.setShowtime(showtime);
        booking.setUser(user);
        //Booking booking = new Booking(showtime,user,bookingDTO.getSeatNumber());
        return bookingRepository.save(booking);
    }

    private boolean isSeatTaken(Integer seatNumber, Long showtimeId) {
        List<Booking> bookings = bookingRepository.findByShowtimeId(showtimeId);

        return bookings.stream().anyMatch(booking -> booking.getSeatNumber().equals(seatNumber));
    }


}
