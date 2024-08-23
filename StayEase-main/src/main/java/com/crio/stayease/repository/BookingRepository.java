package com.crio.stayease.repository;

import com.crio.stayease.entity.Booking;
import com.crio.stayease.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The {@code BookingRepository} interface extends {@code JpaRepository} to provide CRUD operations for {@code Booking} entities.
 *
 * <p>This repository interface enables interaction with the database for {@code Booking} entities and provides additional
 * query methods for retrieving bookings based on their status.</p>
 *
 * <p><b>Inheritance:</b></p>
 * <ul>
 *   <li>{@code JpaRepository<Booking, Long>} - Provides standard CRUD operations and pagination support for the {@code Booking} entity.</li>
 * </ul>
 *
 * <p><b>Custom Query Methods:</b></p>
 * <ul>
 *   <li>{@code List<Booking> findAllByStatus(BookingStatus status)}
 *       - Retrieves a list of bookings that match the specified {@code BookingStatus}. This method allows querying bookings
 *       based on their current status (e.g., BOOKED, CANCELLED, PENDING, FAILED).</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>This repository interface should be used to perform CRUD operations and custom queries related to bookings. It can be
 * injected into services or controllers where booking data needs to be accessed or manipulated.</p>
 *
 * <pre>{@code
 * @Autowired
 * private BookingRepository bookingRepository;
 *
 * // Example usage:
 * List<Booking> bookedBookings = bookingRepository.findAllByStatus(BookingStatus.BOOKED);
 * }</pre>
 *
 * @see com.crio.stayease.entity.Booking
 * @see com.crio.stayease.model.BookingStatus
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Finds all bookings with the specified status.
     *
     * @param status the status of the bookings to retrieve
     * @return a list of bookings with the specified status
     */
    List<Booking> findAllByStatus(BookingStatus status);
}
