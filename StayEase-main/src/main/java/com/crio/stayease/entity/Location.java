package com.crio.stayease.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Location class represents a location entity in the stay ease system.
 *
 * <p>This entity maps to the "locations" table and contains details about a location, including its address,
 * latitude, and longitude. It also represents the relationship with the {@code Hotel} entity.</p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code id} - Unique identifier for the location.</li>
 *   <li>{@code address} - The address of the location. This field is mandatory and cannot be null.</li>
 *   <li>{@code latitude} - The latitude of the location. This field is optional.</li>
 *   <li>{@code longitude} - The longitude of the location. This field is optional.</li>
 *   <li>{@code hotel} - The hotel associated with this location. This represents a one-to-one relationship with the {@code Hotel} entity.</li>
 * </ul>
 *
 * <p><b>Constructor:</b></p>
 * <ul>
 *   <li>{@code Location()} - Default constructor.</li>
 *   <li>{@code Location(Long id, String address, Double latitude, Double longitude, Hotel hotel)} - Constructor to initialize a new location with all fields.</li>
 * </ul>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@code @Entity} - Indicates that this is a JPA entity.</li>
 *   <li>{@code @Table(name = "locations")} - Specifies the table name in the database.</li>
 *   <li>{@code @Id} - Denotes the primary key field.</li>
 *   <li>{@code @GeneratedValue(strategy = GenerationType.IDENTITY)} - Specifies that the primary key value is generated automatically using the identity column strategy.</li>
 *   <li>{@code @Column(nullable = false)} - Specifies that the field is mandatory and cannot be null in the database.</li>
 *   <li>{@code @OneToOne(mappedBy = "location")} - Defines a one-to-one relationship with the {@code Hotel} entity. The {@code hotel} field is the inverse side of the relationship.</li>
 *   <li>{@code @JsonIgnore} - Prevents the {@code hotel} field from being serialized into JSON, avoiding potential infinite recursion issues in bidirectional relationships.</li>
 * </ul>
 *
 * @see com.crio.stayease.entity.Hotel
 */
@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Location {

    /**
     * Unique identifier for the location.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The address of the location.
     *
     * This field is mandatory and cannot be null.
     */
    @Column(nullable = false)
    private String address;

    /**
     * The latitude of the location.
     *
     * This field is optional.
     */
    @Column
    private Double latitude;

    /**
     * The longitude of the location.
     *
     * This field is optional.
     */
    @Column
    private Double longitude;

    /**
     * The hotel associated with this location.
     *
     * This field represents a one-to-one relationship with the {@code Hotel} entity.
     * The {@code hotel} field is mapped by the "location" field in the {@code Hotel} entity.
     *
     * <p>This field is ignored during JSON serialization to prevent infinite recursion.</p>
     */
    @OneToOne(mappedBy = "location")
    @JsonIgnore
    private Hotel hotel;
}
