package com.crio.stayease.services;

import com.crio.stayease.entity.Booking;
import com.crio.stayease.entity.Hotel;
import com.crio.stayease.exchange.BookingRequest;
import com.crio.stayease.model.BookingStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * The {@code BookingService} interface defines the contract for services related to booking management.
 *
 * <p>This interface provides methods for handling various booking operations, including booking creation, updating,
 * cancellation, retrieval, and deletion. It also includes methods for querying available rooms and filtering bookings
 * based on their status.</p>
 *
 * <p><b>Methods:</b></p>
 * <ul>
 *   <li>{@code List<Hotel> availableRooms(Optional<Integer> room)}
 *       - Retrieves a list of hotels with available rooms greater than or equal to the specified number. If the number
 *       of rooms is not provided, it retrieves hotels regardless of room availability.</li>
 *   <li>{@code Booking book(BookingRequest request)}
 *       - Creates a new booking based on the provided {@code BookingRequest}. Returns the created {@code Booking} object.</li>
 *   <li>{@code Booking update(BookingRequest request)}
 *       - Updates an existing booking based on the provided {@code BookingRequest}. Returns the updated {@code Booking} object.</li>
 *   <li>{@code Booking cancel(Long request)}
 *       - Cancels an existing booking identified by the provided ID. Returns the cancelled {@code Booking} object.</li>
 *   <li>{@code List<Booking> get()}
 *       - Retrieves a list of all bookings.</li>
 *   <li>{@code Optional<Booking> getById(Long id)}
 *       - Retrieves a specific booking by its ID. Returns an {@code Optional} containing the {@code Booking} object if found, or empty if not found.</li>
 *   <li>{@code List<Booking> getByStatus(BookingStatus status)}
 *       - Retrieves a list of bookings with the specified {@code BookingStatus}. Useful for filtering bookings based on their current status.</li>
 *   <li>{@code boolean delete(Long id)}
 *       - Deletes a booking identified by the provided ID. Returns {@code true} if the booking was successfully deleted, or {@code false} otherwise.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>This interface should be implemented to provide business logic for managing bookings. It can be used by controllers
 * or other services to interact with booking data and perform various booking-related operations.</p>
 *
 * <pre>{@code
 * @Autowired
 * private BookingService bookingService;
 *
 * // Example usage:
 * List<Hotel> availableHotels = bookingService.availableRooms(Optional.of(5));
 * Booking newBooking = bookingService.book(new BookingRequest(1L, null, BookingStatus.BOOKED));
 * }</pre>
 *
 * @see com.crio.stayease.entity.Booking
 * @see com.crio.stayease.entity.Hotel
 * @see com.crio.stayease.exchange.BookingRequest
 * @see com.crio.stayease.model.BookingStatus
 */
public interface BookingService {

    /**
     * Retrieves a list of hotels with available rooms greater than or equal to the specified number.
     *
     * @param room an {@code Optional} containing the minimum number of rooms required, or empty to retrieve hotels regardless of room availability
     * @return a list of {@code Hotel} objects meeting the room availability criteria
     */
    List<Hotel> availableRooms(Optional<Integer> room);

    /**
     * Creates a new booking based on the provided {@code BookingRequest}.
     *
     * @param request the {@code BookingRequest} containing the {@code `hotelID`} for the new booking.
     *                The {@code BookingRequest} object should include the ID of the hotel where the booking will be made.
     * @return the created {@code Booking} object, which includes details of the newly created booking.
     */
    Booking book(BookingRequest request);

    /**
     * Updates an existing booking based on the provided {@code BookingRequest}.
     * <p>
     * The {@code BookingRequest} should contain the following fields:
     * <ul>
     *   <li>{@code bookingID} (Long): The ID of the booking to be updated.</li>
     *   <li>{@code status} (BookingStatus): The new status to be applied to the booking.</li>
     * </ul>
     * </p>
     * <p>
     * Example of a {@code BookingRequest} JSON payload:
     * </p>
     * <pre>
     * {
     *   "bookingID": 12345,
     *   "status": "CONFIRMED"
     * }
     * </pre>
     *
     * @param request the {@code BookingRequest} containing the updated details for the booking
     * @return the updated {@code Booking} object with the new status applied
     */

    Booking update(BookingRequest request);

    /**
     * Cancels an existing booking identified by the provided ID.
     *
     * @param request the ID of the booking to cancel
     * @return the cancelled {@code Booking} object
     */
    Booking cancel(Long request);

    /**
     * Retrieves a list of all bookings.
     *
     * @return a list of all {@code Booking} objects
     */
    List<Booking> get();

    /**
     * Retrieves a specific booking by its ID.
     *
     * @param id the ID of the booking to retrieve
     * @return an {@code Optional} containing the {@code Booking} object if found, or empty if not found
     */
    Optional<Booking> getById(Long id);

    /**
     * Retrieves a list of bookings filtered by the specified {@code BookingStatus}.
     *
     * <p>
     * This method returns a list of bookings that match the given booking status. The status is specified
     * using a {@link BookingStatus} enumeration. For example, if you want to retrieve all bookings that are
     * currently in a "CONFIRMED" status, you would pass {@code BookingStatus.CONFIRMED} as the parameter.
     * </p>
     *
     * <p>
     * Example:
     * <pre>
     *     // Retrieve all bookings with status 'CONFIRMED'
     *     ResponseEntity<?> response = bookingController.getBookingsByStatus(BookingStatus.CONFIRMED);
     *     List<Booking> bookings = (List<Booking>) response.getBody();
     * </pre>
     * </p>
     *
     * @param status the {@link BookingStatus} to filter bookings by. It represents the current status of bookings
     *               such as {@code CONFIRMED}, {@code CANCELLED}, {@code PENDING}, etc.
     * @return a {@link ResponseEntity} containing a list of {@link Booking} objects that have the specified status.
     */

    List<Booking> getByStatus(BookingStatus status);

    /**
     * Deletes a booking identified by the provided ID.
     *
     * @param id the ID of the booking to delete
     * @return {@code true} if the booking was successfully deleted, or {@code false} otherwise
     */
    boolean delete(Long id);
}
