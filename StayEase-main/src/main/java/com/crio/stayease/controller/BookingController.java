package com.crio.stayease.controller;

import com.crio.stayease.exchange.BookingRequest;
import com.crio.stayease.model.BookingStatus;
import com.crio.stayease.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Controller class for managing bookings in the StayEase application.
 * <p>
 * This controller provides endpoints for performing various operations related to bookings, including:
 * <ul>
 *   <li>Retrieving available hotel rooms based on the required number of rooms.</li>
 *   <li>Booking a hotel.</li>
 *   <li>Updating an existing booking.</li>
 *   <li>Cancelling a booking.</li>
 *   <li>Retrieving all bookings or a booking by its ID.</li>
 *   <li>Retrieving bookings based on their status.</li>
 *   <li>Deleting a booking.</li>
 * </ul>
 * </p>
 *
 * The controller uses the {@link BookingService} to handle the business logic for each operation.
 *
 * @see BookingService
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Retrieves available hotel rooms based on the required number of rooms.
     *
     * @param room Optional parameter specifying the number of required rooms.
     * @return A {@link ResponseEntity} containing the list of available rooms.
     */
    @Operation(
            summary = "Retrieve available hotel rooms",
            description = "Retrieve a list of available hotel rooms based on the required number of rooms.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved available rooms"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/available-rooms")
    public ResponseEntity<?> getHotelHavingRequiredRooms(@RequestParam(required = false) Integer room) {
        return ResponseEntity.ok(bookingService.availableRooms(Optional.ofNullable(room)));
    }

    /**
     * Books a hotel based on the provided booking request.
     *
     * @param request {@link BookingRequest} object containing the booking details.
     * @return A {@link ResponseEntity} containing the details of the booked hotel.
     */
    @Operation(
            summary = "Book a hotel",
            description = "Create a new booking based on the provided booking request.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Booking request details",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    example = "{\n" +
                                            "  \"hotelId\": 1,\n" +
                                            "  \"userId\": 123\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully booked hotel"),
                    @ApiResponse(responseCode = "400", description = "Invalid booking request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping()
    public ResponseEntity<?> bookHotel(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.book(request));
    }

    /**
     * Updates an existing booking based on the provided booking request.
     *
     * @param id      The ID of the booking to update.
     * @param request {@link BookingRequest} object containing the updated booking details.
     * @return A {@link ResponseEntity} containing the updated booking details.
     */
    @Operation(
            summary = "Update an existing booking",
            description = "Update the details of an existing booking based on the provided booking ID and request.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the booking to update", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Booking request details",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    example = "{\n" +
                                            "  \"hotelId\": 1,\n" +
                                            "  \"userId\": 123\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated booking"),
                    @ApiResponse(responseCode = "404", description = "Booking not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid booking request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody BookingRequest request) {
        request.setBookingID(id);
        return ResponseEntity.ok(bookingService.update(request));
    }

    /**
     * Cancels a booking based on the provided booking ID.
     *
     * @param id The ID of the booking to cancel.
     * @return A {@link ResponseEntity} indicating the result of the cancellation operation.
     */
    @Operation(
            summary = "Cancel a booking",
            description = "Cancel a booking based on the provided booking ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the booking to cancel", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully canceled booking"),
                    @ApiResponse(responseCode = "404", description = "Booking not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancel(id));
    }

    /**
     * Retrieves all bookings.
     *
     * @return A {@link ResponseEntity} containing the list of all bookings.
     */
    @Operation(
            summary = "Retrieve all bookings",
            description = "Retrieve a list of all bookings.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved all bookings"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping()
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingService.get());
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id The ID of the booking to retrieve.
     * @return A {@link ResponseEntity} containing the booking details.
     * @throws ResponseStatusException If the booking is not found.
     */
    @Operation(
            summary = "Retrieve booking by ID",
            description = "Retrieve details of a booking by its ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the booking to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved booking"),
                    @ApiResponse(responseCode = "404", description = "Booking not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        return bookingService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
    }

    /**
     * Retrieves bookings based on their status.
     *
     * @param status The status of the bookings to retrieve.
     * @return A {@link ResponseEntity} containing the list of bookings with the specified status.
     */
    @Operation(
            summary = "Retrieve bookings by status",
            description = "Retrieve a list of bookings based on their status.",
            parameters = {
                    @Parameter(name = "status", description = "Status of the bookings to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved bookings by status"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/status")
    public ResponseEntity<?> getBookingsByStatus(@RequestParam BookingStatus status) {
        return ResponseEntity.ok(bookingService.getByStatus(status));
    }

    /**
     * Deletes a booking based on the provided booking ID.
     *
     * @param id The ID of the booking to delete.
     * @return A {@link ResponseEntity} indicating the result of the deletion operation.
     * @throws ResponseStatusException If the booking is not found.
     */
    @Operation(
            summary = "Delete a booking",
            description = "Delete a booking based on the provided booking ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the booking to delete", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted booking"),
                    @ApiResponse(responseCode = "404", description = "Booking not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        if (bookingService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        }
    }
}
