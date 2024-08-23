package com.crio.stayease.exchange;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * The HotelRequest class represents a request for creating or updating hotel information in the stay ease system.
 *
 * <p>This class encapsulates data related to a hotel, including its name, description, number of rooms, address,
 * and location coordinates. It uses validation constraints to ensure the integrity of the input data.</p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code name} - The name of the hotel. This field is mandatory and cannot be empty or blank.</li>
 *   <li>{@code description} - A description of the hotel. This field is mandatory and cannot be empty or blank.</li>
 *   <li>{@code noOfRooms} - The number of rooms in the hotel. This field is optional and uses {@code Optional<Integer>} to handle the absence of a value.</li>
 *   <li>{@code address} - The address of the hotel. This field is mandatory and cannot be null.</li>
 *   <li>{@code latitude} - The latitude coordinate of the hotel's location. This field is optional.</li>
 *   <li>{@code longitude} - The longitude coordinate of the hotel's location. This field is optional.</li>
 * </ul>
 *
 * <p><b>Validation Constraints:</b></p>
 * <ul>
 *   <li>{@code @NotEmpty} - Ensures that {@code name} and {@code description} fields are not empty.</li>
 *   <li>{@code @NotBlank} - Ensures that {@code name} and {@code description} fields are not blank.</li>
 *   <li>{@code @NotNull} - Ensures that the {@code address} field is not null.</li>
 * </ul>
 *
 * <p><b>Constructor:</b></p>
 * <ul>
 *   <li>{@code HotelRequest()} - Default constructor.</li>
 *   <li>{@code HotelRequest(String name, String description, Optional<Integer> noOfRooms, String address, Double latitude, Double longitude)} - Constructor to initialize a new hotel request with all fields.</li>
 * </ul>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields as parameters.</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-arguments constructor.</li>
 *   <li>{@code @Data} - Provides getters, setters, {@code toString()}, {@code equals()}, and {@code hashCode()} methods.</li>
 *   <li>{@code @NotEmpty} - Ensures the field is not empty (used for {@code name} and {@code description}).</li>
 *   <li>{@code @NotBlank} - Ensures the field is not blank (used for {@code name} and {@code description}).</li>
 *   <li>{@code @NotNull} - Ensures the field is not null (used for {@code address}).</li>
 * </ul>
 *
 * @see java.util.Optional
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelRequest {

    /**
     * The name of the hotel.
     *
     * This field is mandatory and cannot be empty or blank.
     */
    @NotEmpty(message = "Hotel name cannot be empty")
    @NotBlank(message = "Hotel name cannot be blank")
    private String name;

    /**
     * A description of the hotel.
     *
     * This field is mandatory and cannot be empty or blank.
     */
    @NotEmpty(message = "Description cannot be empty")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    /**
     * The number of rooms in the hotel.
     *
     * This field is optional and uses {@code Optional<Integer>} to handle the absence of a value.
     */
    private Optional<Integer> noOfRooms;

    /**
     * The address of the hotel.
     *
     * This field is mandatory and cannot be null.
     */
    @NotNull(message = "Address cannot be null")
    private String address;

    /**
     * The latitude coordinate of the hotel's location.
     *
     * This field is optional.
     */
    private Double latitude;

    /**
     * The longitude coordinate of the hotel's location.
     *
     * This field is optional.
     */
    private Double longitude;
}
