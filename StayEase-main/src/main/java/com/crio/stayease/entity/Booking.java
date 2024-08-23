package com.crio.stayease.entity;

import com.crio.stayease.model.BookingStatus;
import com.crio.stayease.security.entity.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Booking class represents a booking entity in the stay ease system.
 *
 * <p>This entity maps to the "bookings" table and contains details about a booking made by a user at a hotel.</p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@code Users} - Represents the user who made the booking.</li>
 *   <li>{@code Hotel} - Represents the hotel where the booking was made.</li>
 *   <li>{@code BookingStatus} - Enum representing the status of the booking.</li>
 * </ul>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code id} - Unique identifier for the booking.</li>
 *   <li>{@code user} - The user who made the booking. This field is a foreign key to the "users" table.</li>
 *   <li>{@code hotel} - The hotel where the booking was made. This field is a foreign key to the "hotels" table.</li>
 *   <li>{@code status} - The current status of the booking, represented by the {@code BookingStatus} enum.</li>
 * </ul>
 *
 * <p><b>Constructor:</b></p>
 * <ul>
 *   <li>{@code Booking(Users user, Hotel hotel, BookingStatus bookingStatus)} - Constructor to initialize a new booking with user, hotel, and status.</li>
 * </ul>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@code @Entity} - Indicates that this is a JPA entity.</li>
 *   <li>{@code @Table(name = "bookings")} - Specifies the table name in the database.</li>
 *   <li>{@code @Id} - Denotes the primary key field.</li>
 *   <li>{@code @GeneratedValue} - Specifies that the primary key value is generated automatically.</li>
 *   <li>{@code @ManyToOne} - Defines a many-to-one relationship with the {@code Users} and {@code Hotel} entities.</li>
 *   <li>{@code @JoinColumn} - Specifies the foreign key column for the relationship.</li>
 *   <li>{@code @Enumerated(EnumType.STRING)} - Indicates that the {@code BookingStatus} should be stored as a string in the database.</li>
 *   <li>{@code @JsonBackReference} - Manages bidirectional relationships in JSON serialization, preventing infinite recursion.</li>
 * </ul>
 *
 * @see com.crio.stayease.model.BookingStatus
 * @see com.crio.stayease.security.entity.Users
 * @see com.crio.stayease.entity.Hotel
 */
@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {

    /**
     * Unique identifier for the booking.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The user who made the booking.
     *
     * This field is a foreign key to the "users" table.
     */
    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    @JsonBackReference
    private Users user;

    /**
     * The hotel where the booking was made.
     *
     * This field is a foreign key to the "hotels" table.
     */
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;

    /**
     * The current status of the booking.
     *
     * This field uses the {@code BookingStatus} enum to represent various statuses.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    /**
     * Constructor to initialize a new booking with user, hotel, and status.
     *
     * @param user the user who made the booking
     * @param hotel the hotel where the booking was made
     * @param bookingStatus the status of the booking
     */
    public Booking(Users user, Hotel hotel, BookingStatus bookingStatus) {
        this.user = user;
        this.hotel = hotel;
        this.status = bookingStatus;
    }
}
