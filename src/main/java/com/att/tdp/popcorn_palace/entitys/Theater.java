package com.att.tdp.popcorn_palace.entitys;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity(name = "theater")
@Table(name = "theater")
public class Theater {

    @Id
    @GeneratedValue
            (
                    strategy = GenerationType.IDENTITY
            )
    private long id;

    @Column(name = "name",nullable = false,length = 60, unique = true)
    private String name;

    @Column(name = "total_sits",nullable = false)
    private Integer totalSits;
    
    @JsonIgnore
    @OneToMany(mappedBy = "theater",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Showtime> showtimes = new HashSet<>();

    public Theater() {

    }

    public Theater(String name,Integer totalSits) {
        this.name = name;
        this.totalSits = totalSits;
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSits() {
        return totalSits;
    }

    public void setTotalSits(int totalSits) {
        this.totalSits = totalSits;
    }

    public Set<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(Set<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}
