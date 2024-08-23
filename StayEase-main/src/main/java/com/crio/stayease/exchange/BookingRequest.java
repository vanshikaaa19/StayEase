package com.crio.stayease.exchange;

import com.crio.stayease.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The BookingRequest class represents a request for booking operations in the stay ease system.
 *
 * <p>This class is used to encapsulate data related to a booking request, including the hotel ID, booking ID, and
 * the current status of the booking.</p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code hotelID} - The unique identifier of the hotel associated with the booking request.</li>
 *   <li>{@code bookingID} - The unique identifier of the booking. This field may be used for updating or retrieving
 *       specific bookings.</li>
 *   <li>{@code status} - The current status of the booking, represented by the {@code BookingStatus} enumeration.</li>
 * </ul>
 *
 * <p><b>Constructor:</b></p>
 * <ul>
 *   <li>{@code BookingRequest()} - Default constructor.</li>
 *   <li>{@code BookingRequest(Long hotelID, Long bookingID, BookingStatus status)} - Constructor to initialize a new booking request with all fields.</li>
 * </ul>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields as parameters.</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-arguments constructor.</li>
 *   <li>{@code @Data} - Provides getters, setters, {@code toString()}, {@code equals()}, and {@code hashCode()} methods.</li>
 * </ul>
 *
 * @see com.crio.stayease.model.BookingStatus
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingRequest {

    /**
     * The unique identifier of the hotel associated with the booking request.
     */
    private Long hotelID;

    /**
     * The unique identifier of the booking.
     *
     * This field may be used for updating or retrieving specific bookings.
     */
    private Long bookingID;

    /**
     * The current status of the booking.
     *
     * This field is represented by the {@code BookingStatus} enumeration.
     */
    private BookingStatus status;
}
