package com.crio.stayease.services;

import com.crio.stayease.entity.Hotel;
import com.crio.stayease.exchange.HotelRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The {@code HotelService} interface defines the contract for services related to hotel management.
 *
 * <p>This interface provides methods for handling various hotel operations, including creation, updating, retrieval,
 * and deletion of hotel records.</p>
 *
 * <p><b>Methods:</b></p>
 * <ul>
 *   <li>{@code Hotel create(HotelRequest hotel)}
 *       - Creates a new hotel based on the provided {@code HotelRequest}. Returns the created {@code Hotel} object.</li>
 *   <li>{@code Hotel update(Long id, Map<String, Object> hotel)}
 *       - Updates an existing hotel identified by the provided ID. The updates are specified in the {@code Map} of hotel attributes.
 *       Returns the updated {@code Hotel} object.</li>
 *   <li>{@code List<Hotel> get()}
 *       - Retrieves a list of all hotels.</li>
 *   <li>{@code Optional<Hotel> get(Long id)}
 *       - Retrieves a specific hotel by its ID. Returns an {@code Optional} containing the {@code Hotel} object if found, or empty if not found.</li>
 *   <li>{@code boolean delete(Long id)}
 *       - Deletes a hotel identified by the provided ID. Returns {@code true} if the hotel was successfully deleted, or {@code false} otherwise.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>This interface should be implemented to provide business logic for managing hotels. It can be used by controllers
 * or other services to interact with hotel data and perform various hotel-related operations.</p>
 *
 * <pre>{@code
 * @Autowired
 * private HotelService hotelService;
 *
 * // Example usage:
 * HotelRequest newHotelRequest = new HotelRequest("Hotel Name", "Hotel Description", 100, "Address", 40.7128, -74.0060);
 * Hotel newHotel = hotelService.create(newHotelRequest);
 * Optional<Hotel> existingHotel = hotelService.get(1L);
 * boolean isDeleted = hotelService.delete(2L);
 * }</pre>
 *
 * @see com.crio.stayease.entity.Hotel
 * @see com.crio.stayease.exchange.HotelRequest
 */
public interface HotelService {

    /**
     * Creates a new hotel based on the provided {@code HotelRequest}.
     * <p>
     * The {@code HotelRequest} should contain the following fields:
     * <ul>
     *   <li>{@code name} - The name of the hotel.</li>
     *   <li>{@code description} - A description of the hotel.</li>
     *   <li>{@code noOfRooms} - The number of rooms available in the hotel.</li>
     *   <li>{@code address} - The address of the hotel.</li>
     *   <li>{@code latitude} - The latitude coordinate of the hotel's location.</li>
     *   <li>{@code longitude} - The longitude coordinate of the hotel's location.</li>
     * </ul>
     * </p>
     * <p>
     * Example {@code HotelRequest} payload:
     * </p>
     * <pre>
     * {
     *   "name": "Grand Hotel",
     *   "description": "A luxurious hotel with stunning views.",
     *   "noOfRooms": 150,
     *   "address": "1234 Luxury Lane, Suite 567, Cityville, ST, 12345",
     *   "latitude": 37.7749,
     *   "longitude": -122.4194
     * }
     * </pre>
     *
     * @param hotel the {@code HotelRequest} containing details for the new hotel
     * @return the created {@code Hotel} object
     */

    Hotel create(HotelRequest hotel);

    /**
     * Updates an existing hotel identified by the provided ID. The updates are specified in the {@code Map} of hotel attributes.
     * <p>
     * The {@code Map} can contain the following keys to update the corresponding attributes of the hotel:
     * <ul>
     *   <li>{@code "name"}: The name of the hotel (type: {@code String})</li>
     *   <li>{@code "description"}: A brief description of the hotel (type: {@code String})</li>
     *   <li>{@code "noOfRooms"}: The number of rooms available in the hotel (type: {@code Integer})</li>
     *   <li>{@code "address"}: The address of the hotel (type: {@code String})</li>
     *   <li>{@code "latitude"}: The latitude coordinate of the hotel's location (type: {@code Double})</li>
     *   <li>{@code "longitude"}: The longitude coordinate of the hotel's location (type: {@code Double})</li>
     * </ul>
     * </p>
     * <p>
     * Example:
     * </p>
     * <pre>
     * {
     *   "name": "Grand Hotel",
     *   "description": "A luxurious hotel in the city center",
     *   "noOfRooms": 100,
     *   "address": "123 Luxury Ave, Cityville",
     *   "latitude": 40.7128,
     *   "longitude": -74.0060
     * }
     * </pre>
     *
     * @param id     the ID of the hotel to update
     * @param hotel  a {@code Map} containing the attributes to update in the hotel record
     * @return the updated {@code Hotel} object
     */

    Hotel update(Long id, Map<String, Object> hotel);

    /**
     * Retrieves a list of all hotels.
     *
     * @return a list of all {@code Hotel} objects
     */
    List<Hotel> get();

    /**
     * Retrieves a specific hotel by its ID.
     *
     * @param id the ID of the hotel to retrieve
     * @return an {@code Optional} containing the {@code Hotel} object if found, or empty if not found
     */
    Optional<Hotel> get(Long id);

    /**
     * Deletes a hotel identified by the provided ID.
     *
     * @param id the ID of the hotel to delete
     * @return {@code true} if the hotel was successfully deleted, or {@code false} otherwise
     */
    boolean delete(Long id);
}
