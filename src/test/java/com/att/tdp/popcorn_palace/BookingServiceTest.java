package com.att.tdp.popcorn_palace;


import com.att.tdp.popcorn_palace.dto.BookingDTO;
import com.att.tdp.popcorn_palace.entitys.*;
import com.att.tdp.popcorn_palace.exception.SeatAlreadyBookedException;
import com.att.tdp.popcorn_palace.exception.SeatOutOfRangeException;
import com.att.tdp.popcorn_palace.repository.BookingRepository;

import com.att.tdp.popcorn_palace.repository.UsersRepository;
import com.att.tdp.popcorn_palace.service.BookingService;

import com.att.tdp.popcorn_palace.service.ShowtimeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private  UsersRepository usersRepository;
    @Mock
    private  ShowtimeService showtimeService;

    @InjectMocks
    private BookingService bookingService;

    private UUID userId;
    private Users user;
    private Showtime showtime;
    private Theater theater;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new Users(userId, "Ron");
        theater = new Theater("Lev", 20);
        showtime = new Showtime();
        showtime.setTheater(theater);
    }


    @Test
    void testCreateBooking_Success() {
        Booking booking = new Booking(UUID.randomUUID(), showtime, user, 12);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(showtimeService.getShowTimeById(1L)).thenReturn(showtime);
        when(bookingRepository.findByShowtimeId(1L)).thenReturn(Collections.emptyList());
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingDTO bookingDTO = new BookingDTO(1L, 12, userId);
        Booking createdBooking = bookingService.createBooking(bookingDTO);

        assertNotNull(createdBooking);
        assertEquals(12, createdBooking.getSeatNumber());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testCreateBookingSitOverMaxRange_Fail() {
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(showtimeService.getShowTimeById(1L)).thenReturn(showtime);

        BookingDTO bookingDTO = new BookingDTO(1L, 22, userId);

        assertThrows(
                SeatOutOfRangeException.class,
                () -> bookingService.createBooking(bookingDTO)
        );

        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void testCreateBookingOverlapSit_Success() {
        List<Booking> bookingList = List.of(
                new Booking(UUID.randomUUID(), showtime, user, 1),
                new Booking(UUID.randomUUID(), showtime, user, 2),
                new Booking(UUID.randomUUID(), showtime, user, 3)
        );

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(showtimeService.getShowTimeById(1L)).thenReturn(showtime);
        when(bookingRepository.findByShowtimeId(1L)).thenReturn(bookingList);

        BookingDTO bookingDTO = new BookingDTO(1L, 3, userId);

        assertThrows(
                SeatAlreadyBookedException.class,
                () -> bookingService.createBooking(bookingDTO)
        );

        verify(bookingRepository, never()).save(any(Booking.class));
    }

}
