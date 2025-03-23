package com.att.tdp.popcorn_palace.entitys;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "booking")
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("bookingId")
    private UUID id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;



    public Booking() {

    }
    public Booking(UUID uuid,Showtime showtime, Users users, Integer seatNumber) {
        this.id =uuid;
        this.showtime = showtime;
        this.users = users;
        this.seatNumber = seatNumber;
    }

    public UUID getId() {
        return id;
    }


    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}
