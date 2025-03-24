package com.att.tdp.popcorn_palace.dto.response;

import java.util.UUID;

public class BookingResponse {
    final UUID bookingId;

    public BookingResponse(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UUID getBookingId() {
        return bookingId;
    }
}
