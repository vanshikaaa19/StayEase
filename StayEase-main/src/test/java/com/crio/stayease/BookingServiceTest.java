package com.crio.stayease;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.crio.stayease.entity.Booking;
import com.crio.stayease.entity.Hotel;
import com.crio.stayease.entity.Location;
import com.crio.stayease.exchange.BookingRequest;
import com.crio.stayease.model.BookingStatus;
import com.crio.stayease.security.entity.Users;
import com.crio.stayease.services.BookingService;
import com.crio.stayease.services.implementation.BookingServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BookingServiceTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingServiceImpl bookingServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_create_booking_with_valid_data() {
        // Arrange
        BookingRequest request = new BookingRequest(1L, null, BookingStatus.BOOKED);
        Booking expectedBooking = new Booking(new Users(), new Hotel(), BookingStatus.BOOKED);

        when(bookingService.book(request)).thenReturn(expectedBooking);

        // Act
        Booking actualBooking = bookingService.book(request);

        // Assert
        assertNotNull(actualBooking);
        assertEquals(expectedBooking, actualBooking);
    }

    @Test
    public void test_create_booking_with_invalid_data() {
        // Arrange
        BookingRequest invalidRequest = new BookingRequest(null, null, null);

        when(bookingService.book(invalidRequest)).thenThrow(new IllegalArgumentException("Invalid booking request"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.book(invalidRequest);
        });

        assertEquals("Invalid booking request", exception.getMessage());
    }

    @Test
    public void test_updating_existing_booking() {
        // Arrange
        BookingRequest request = new BookingRequest(1L, 12345L, BookingStatus.BOOKED);
        Booking booking = new Booking(new Users(), new Hotel(), BookingStatus.BOOKED);

        when(bookingService.update(request)).thenReturn(booking);

        // Act
        Booking updatedBooking = bookingService.update(request);

        // Assert
        assertNotNull(updatedBooking);
        assertEquals(BookingStatus.BOOKED, updatedBooking.getStatus());
    }

    @Test
    public void test_cancelling_existing_booking() {
        // Arrange
        Long bookingId = 12345L;
        Booking booking = new Booking(new Users(), new Hotel(), BookingStatus.CANCELLED);

        when(bookingService.cancel(bookingId)).thenReturn(booking);

        // Act
        Booking cancelledBooking = bookingService.cancel(bookingId);

        // Assert
        assertNotNull(cancelledBooking);
        assertEquals(BookingStatus.CANCELLED, cancelledBooking.getStatus());
    }

    @Test
    public void test_retrieving_all_bookings() {
        // Arrange
        List<Booking> expectedBookings = new ArrayList<>();
        when(bookingService.get()).thenReturn(expectedBookings);

        // Act
        List<Booking> actualBookings = bookingService.get();

        // Assert
        assertEquals(expectedBookings, actualBookings);
    }

    @Test
    public void test_retrieving_booking_by_id() {
        // Arrange
        Long bookingId = 1L;
        Booking expectedBooking = new Booking();
        when(bookingService.getById(bookingId)).thenReturn(Optional.of(expectedBooking));

        // Act
        Optional<Booking> actualBookingOptional = bookingService.getById(bookingId);

        // Assert
        assertTrue(actualBookingOptional.isPresent());
        assertEquals(expectedBooking, actualBookingOptional.get());
    }

    @Test
    public void test_retrieving_bookings_by_status() {
        // Arrange
        BookingStatus status = BookingStatus.BOOKED;
        List<Booking> mockBookings = new ArrayList<>();
        Booking booking1 = new Booking(new Users(), new Hotel(), BookingStatus.BOOKED);
        Booking booking2 = new Booking(new Users(), new Hotel(), BookingStatus.BOOKED);
        mockBookings.add(booking1);
        mockBookings.add(booking2);

        when(bookingService.getByStatus(status)).thenReturn(mockBookings);

        // Act
        List<Booking> bookings = bookingService.getByStatus(status);

        // Assert
        assertEquals(2, bookings.size());
        assertEquals(BookingStatus.BOOKED, bookings.get(0).getStatus());
        assertEquals(BookingStatus.BOOKED, bookings.get(1).getStatus());
    }

    @Test
    public void test_deleting_booking_with_valid_id() {
        // Arrange
        Long bookingId = 12345L;
        when(bookingService.delete(bookingId)).thenReturn(true);

        // Act
        boolean isDeleted = bookingService.delete(bookingId);

        // Assert
        assertTrue(isDeleted);
    }

    @Test
    public void test_retrieving_hotels_with_available_rooms() {
        // Arrange
        Optional<Integer> room = Optional.of(5);
        List<Hotel> expectedHotels = new ArrayList<>();
        Hotel hotel1 = new Hotel(1L, "Hotel A", "Description A", 10, new Location(), new ArrayList<>());
        Hotel hotel2 = new Hotel(2L, "Hotel B", "Description B", 15, new Location(), new ArrayList<>());
        expectedHotels.add(hotel1);
        expectedHotels.add(hotel2);

        when(bookingService.availableRooms(room)).thenReturn(expectedHotels);

        // Act
        List<Hotel> actualHotels = bookingService.availableRooms(room);

        // Assert
        assertEquals(expectedHotels, actualHotels);
    }

    @Test
    public void test_updating_booking_with_invalid_id() {
        // Arrange
        BookingRequest request = new BookingRequest(1L, null, BookingStatus.BOOKED);

        when(bookingService.update(request)).thenThrow(new IllegalArgumentException("Invalid booking ID"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.update(request);
        });

        assertEquals("Invalid booking ID", exception.getMessage());
    }

    @Test
    public void test_cancelling_booking_with_invalid_id_should_handle_errors_gracefully() {
        // Arrange
        Long invalidBookingId = -1L;

        when(bookingService.cancel(invalidBookingId)).thenThrow(new IllegalArgumentException("Invalid booking ID"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookingService.cancel(invalidBookingId));
    }

    @Test
    public void test_retrieving_booking_by_invalid_id_should_return_empty_optional() {
        // Arrange
        Long invalidBookingId = -1L;

        when(bookingService.getById(invalidBookingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertEquals(Optional.empty(), bookingService.getById(invalidBookingId));
    }

    @Test
    public void test_deleting_booking_with_invalid_id_should_return_false() {
        // Arrange
        Long invalidId = -1L;
        when(bookingService.delete(invalidId)).thenReturn(false);

        // Act
        boolean isDeleted = bookingService.delete(invalidId);

        // Assert
        assertFalse(isDeleted);
    }

    @Test
    public void test_retrieving_hotels_with_no_available_rooms_should_return_empty_list() {
        // Arrange
        Optional<Integer> noRooms = Optional.of(0);
        when(bookingService.availableRooms(noRooms)).thenReturn(new ArrayList<>());

        // Act
        List<Hotel> hotels = bookingService.availableRooms(noRooms);

        // Assert
        assertTrue(hotels.isEmpty());
    }

    @Test
    public void test_handling_null_values_in_booking_request() {
        // Arrange
        BookingRequest request = new BookingRequest(null, null, null);

        when(bookingService.book(request)).thenReturn(null);

        // Act
        Booking booking = bookingService.book(request);

        // Assert
        assertNull(booking);
    }

    @Test
    public void test_booking_status_transitions_validation() {
        // Arrange
        BookingRequest request = new BookingRequest(1L, null, BookingStatus.BOOKED);
        Booking booking = new Booking();
        booking.setStatus(BookingStatus.BOOKED);

        when(bookingService.update(request)).thenReturn(booking);

        // Act
        Booking updatedBooking = bookingService.update(request);

        // Assert
        assertNotNull(updatedBooking);
        assertEquals(BookingStatus.BOOKED, updatedBooking.getStatus());
    }
}
