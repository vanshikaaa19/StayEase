package com.crio.stayease.controller;

import com.crio.stayease.exchange.HotelRequest;
import com.crio.stayease.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller class for managing hotels in the StayEase application.
 * <p>
 * This controller provides endpoints for performing various operations related to hotels, including:
 * <ul>
 *   <li>Retrieving all hotels.</li>
 *   <li>Retrieving a specific hotel by its ID.</li>
 *   <li>Saving a new hotel.</li>
 *   <li>Updating an existing hotel.</li>
 *   <li>Deleting a hotel by its ID.</li>
 * </ul>
 * </p>
 *
 * The controller uses the {@link HotelService} to handle the business logic for each operation.
 *
 * @see HotelService
 */
@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    /**
     * Retrieves a list of all hotels.
     *
     * @return A {@link ResponseEntity} containing a list of all hotels.
     */
    @Operation(
            summary = "Get all hotels",
            description = "Retrieve a list of all hotels.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of hotels"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllHotels() {
        return ResponseEntity.ok(hotelService.get());
    }

    /**
     * Retrieves a specific hotel by its ID.
     *
     * @param id The ID of the hotel to retrieve.
     * @return A {@link ResponseEntity} containing the details of the specified hotel.
     */
    @Operation(
            summary = "Get hotel by ID",
            description = "Retrieve details of a hotel by its ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the hotel to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved hotel"),
                    @ApiResponse(responseCode = "404", description = "Hotel not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable long id) {
        return ResponseEntity.ok(hotelService.get(id));
    }

    /**
     * Saves a new hotel with the provided details.
     *
     * @param hotel {@link HotelRequest} object containing the details of the hotel to be saved.
     * @return A {@link ResponseEntity} containing the saved hotel details.
     */
    @Operation(
            summary = "Save a new hotel",
            description = "Create a new hotel based on the provided HotelRequest.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "HotelRequest object to be created",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    example = "{\n" +
                                            "  \"name\": \"Grand Hotel\",\n" +
                                            "  \"description\": \"A luxurious hotel with stunning views.\",\n" +
                                            "  \"noOfRooms\": 150,\n" +
                                            "  \"address\": \"1234 Luxury Lane, Suite 567, Cityville, ST, 12345\",\n" +
                                            "  \"latitude\": 37.7749,\n" +
                                            "  \"longitude\": -122.4194\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully created hotel"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> saveHotel(@Valid @RequestBody HotelRequest hotel) {
        return ResponseEntity.ok(hotelService.create(hotel));
    }

    /**
     * Updates an existing hotel with the provided details.
     *
     * @param id     The ID of the hotel to update.
     * @param hotel  A {@link Map} containing the updated details of the hotel.
     * @return A {@link ResponseEntity} containing the updated hotel details.
     */
    @Operation(
            summary = "Update hotel by ID",
            description = "Update details of an existing hotel by its ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the hotel to update", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Map of hotel fields to update",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    example = "{\n" +
                                            "  \"name\": \"Updated Hotel Name\",\n" +
                                            "  \"description\": \"Updated description\",\n" +
                                            "  \"noOfRooms\": 200,\n" +
                                            "  \"address\": \"Updated address\",\n" +
                                            "  \"latitude\": 37.7749,\n" +
                                            "  \"longitude\": -122.4194\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated hotel"),
                    @ApiResponse(responseCode = "404", description = "Hotel not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable Long id, @RequestBody Map<String, Object> hotel) {
        return ResponseEntity.ok(hotelService.update(id, hotel));
    }

    /**
     * Deletes a hotel by its ID.
     *
     * @param id The ID of the hotel to delete.
     * @return A {@link ResponseEntity} indicating the result of the deletion operation.
     */
    @Operation(
            summary = "Delete hotel by ID",
            description = "Delete a hotel by its ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the hotel to delete", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted hotel"),
                    @ApiResponse(responseCode = "404", description = "Hotel not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotelById(@PathVariable long id) {
        return ResponseEntity.ok(hotelService.delete(id));
    }
}
