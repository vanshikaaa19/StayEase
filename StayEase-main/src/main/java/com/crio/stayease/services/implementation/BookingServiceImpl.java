package com.crio.stayease.services.implementation;

import com.crio.stayease.entity.Booking;
import com.crio.stayease.entity.Hotel;
import com.crio.stayease.exchange.BookingRequest;
import com.crio.stayease.model.BookingStatus;
import com.crio.stayease.repository.BookingRepository;
import com.crio.stayease.repository.HotelRepository;
import com.crio.stayease.security.entity.Users;
import com.crio.stayease.security.repository.UserRepository;
import com.crio.stayease.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link BookingService} interface for managing bookings.
 *
 * <p>This service provides operations for booking management, including checking available rooms, creating,
 * updating, and cancelling bookings. It also includes methods for retrieving all bookings or filtering them by
 * status.</p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@code UserRepository} - Repository for user management.</li>
 *   <li>{@code HotelRepository} - Repository for hotel management.</li>
 *   <li>{@code BookingRepository} - Repository for booking management.</li>
 * </ul>
 *
 * @see com.crio.stayease.services.BookingService
 * @see com.crio.stayease.entity.Booking
 * @see com.crio.stayease.entity.Hotel
 * @see com.crio.stayease.exchange.BookingRequest
 * @see com.crio.stayease.model.BookingStatus
 */
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

    /**
     * Retrieves a list of hotels with a number of rooms greater than or equal to the specified value.
     *
     * @param room an {@code Optional<Integer>} representing the minimum number of rooms required; defaults to 1 if empty
     * @return a list of {@link Hotel} objects meeting the room criteria
     */
    @Override
    public List<Hotel> availableRooms(Optional<Integer> room) {
        return hotelRepository.findHotelsByNoOfRoomsGreaterThanEqual(room.orElse(1));
    }

    /**
     * Books a room in a specified hotel for the currently authenticated user.
     *
     * @param request a {@link BookingRequest} containing the hotel ID and booking details
     * @return a {@link Booking} object representing the created booking
     * @throws ResponseStatusException if the hotel is not found, the room is not available, or the user is not found
     */
    @Override
    public Booking book(BookingRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (request.getHotelID() != null) {
            Optional<Hotel> oh = hotelRepository.findById(request.getHotelID());
            if (oh.isPresent()) {
                Hotel hotel = oh.get();
                if (hotel.getNoOfRooms() >= 1) {
                    Users user = userRepository.findByEmail(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
                    hotel.setNoOfRooms(hotel.getNoOfRooms() - 1);
                    hotelRepository.save(hotel);
                    return bookingRepository.save(new Booking(user, hotel, BookingStatus.BOOKED));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not available");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
    }

    /**
     * Updates the status of an existing booking.
     *
     * @param request a {@link BookingRequest} containing the booking ID and new status
     * @return the updated {@link Booking} object
     * @throws ResponseStatusException if the booking is not found or an attempt is made to cancel an already cancelled booking
     */
    @Override
    public Booking update(BookingRequest request) {
        if (request.getBookingID() != null && request.getStatus() != null) {
            if (request.getStatus().equals(BookingStatus.CANCELLED))
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Booking can't be cancelled");
            Optional<Booking> b = bookingRepository.findById(request.getBookingID());
            if (b.isPresent()) {
                Booking booking = b.get();
                booking.setStatus(request.getStatus());
                return bookingRepository.save(booking);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
    }

    /**
     * Cancels a booking and restores the room to the hotel's availability.
     *
     * @param id the ID of the booking to cancel
     * @return the updated {@link Booking} object with status set to CANCELLED
     * @throws ResponseStatusException if the booking is not found
     */
    @Override
    public Booking cancel(Long id) {
        Optional<Booking> b = bookingRepository.findById(id);
        if (b.isPresent()) {
            Booking booking = b.get();
            booking.setStatus(BookingStatus.CANCELLED);
            Optional<Hotel> oh = hotelRepository.findById(booking.getHotel().getId());
            if (oh.isPresent()) {
                Hotel hotel = oh.get();
                hotel.setNoOfRooms(hotel.getNoOfRooms() + 1);
                hotelRepository.save(hotel);
            }
            return bookingRepository.save(booking);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
    }

    /**
     * Retrieves all bookings.
     *
     * @return a list of all {@link Booking} objects
     */
    @Override
    public List<Booking> get() {
        return bookingRepository.findAll();
    }

    /**
     * Retrieves bookings based on their status.
     *
     * @param status the {@link BookingStatus} to filter bookings by
     * @return a list of {@link Booking} objects with the specified status
     */
    @Override
    public List<Booking> getByStatus(BookingStatus status) {
        return bookingRepository.findAllByStatus(status);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id the ID of the booking to retrieve
     * @return an {@code Optional} containing the {@link Booking} object if found, or empty if not
     */
    @Override
    public Optional<Booking> getById(Long id) {
        return bookingRepository.findById(id);
    }

    /**
     * Deletes a booking by its ID.
     *
     * @param id the ID of the booking to delete
     * @return {@code true} if the booking was deleted successfully, {@code false} otherwise
     * @throws ResponseStatusException if the booking is not found
     */
    @Override
    public boolean delete(Long id) {
        if (bookingRepository.findById(id).isPresent()) {
            bookingRepository.deleteById(id);
            return true;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
    }
}
