package com.crio.stayease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a hotel entity in the StayEase system.
 *
 * <p>This entity maps to the "hotels" table and contains details about a hotel, including its name, description,
 * number of rooms, location, and associated bookings.</p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@code Location} - Represents the location of the hotel.</li>
 *   <li>{@code Booking} - Represents bookings made at the hotel.</li>
 * </ul>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code id} - Unique identifier for the hotel.</li>
 *   <li>{@code name} - The name of the hotel. This field is mandatory and cannot be null.</li>
 *   <li>{@code description} - A description of the hotel. This field is mandatory and cannot be null.</li>
 *   <li>{@code noOfRooms} - The number of rooms available in the hotel. This field is mandatory and cannot be null.</li>
 *   <li>{@code location} - The location of the hotel, represented by a {@code Location} entity. This field represents
 *       a one-to-one relationship with the {@code Location} entity, with the location information stored in the "location_id" column.</li>
 *   <li>{@code bookings} - A list of bookings associated with the hotel. This represents a one-to-many relationship with
 *       the {@code Booking} entity. The bookings are fetched lazily to optimize performance.</li>
 * </ul>
 *
 * <p><b>Constructor:</b></p>
 * <ul>
 *   <li>{@code Hotel()} - Default constructor.</li>
 *   <li>{@code Hotel(Long id, String name, String description, int noOfRooms, Location location, List<Booking> bookings)}
 *       - Constructor to initialize a new hotel with all fields.</li>
 * </ul>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@code @Entity} - Indicates that this is a JPA entity.</li>
 *   <li>{@code @Table(name = "hotels")} - Specifies the table name in the database.</li>
 *   <li>{@code @Id} - Denotes the primary key field.</li>
 *   <li>{@code @GeneratedValue} - Specifies that the primary key value is generated automatically.</li>
 *   <li>{@code @Column(nullable = false)} - Specifies that the field is mandatory and cannot be null in the database.</li>
 *   <li>{@code @OneToOne} - Defines a one-to-one relationship with the {@code Location} entity.</li>
 *   <li>{@code @OneToMany} - Defines a one-to-many relationship with the {@code Booking} entity. Fetch type is set to
 *       {@code LAZY} to improve performance by loading the bookings only when needed.</li>
 *   <li>{@code @JoinColumn} - Specifies the foreign key column for the relationship.</li>
 *   <li>{@code @Builder} - Enables the builder pattern for creating instances of the class.</li>
 *   <li>{@code @Version} - Optimistic lock version field to handle concurrent updates.</li>
 * </ul>
 *
 * @see com.crio.stayease.entity.Location
 * @see com.crio.stayease.entity.Booking
 */
@Entity
@Table(name = "hotels")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Hotel {

    /**
     * Unique identifier for the hotel.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The name of the hotel.
     * 
     * This field is mandatory and cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * A description of the hotel.
     * 
     * This field is mandatory and cannot be null.
     */
    @Column(nullable = false)
    private String description;


    /**
     * The number of rooms available in the hotel.
     * 
     * This field is mandatory and cannot be null.
     */
    @Column(nullable = false)
    private int noOfRooms;

    /**
     * The location of the hotel.
     * 
     * This field represents a one-to-one relationship with the {@code Location} entity.
     * The location information is stored in the "location_id" column.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    /**
     * A list of bookings associated with the hotel.
     * 
     * This field represents a one-to-many relationship with the {@code Booking} entity.
     * The bookings are fetched lazily to optimize performance.
     */
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
