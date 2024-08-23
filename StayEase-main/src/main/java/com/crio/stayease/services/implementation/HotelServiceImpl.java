package com.crio.stayease.services.implementation;

import com.crio.stayease.entity.Hotel;
import com.crio.stayease.entity.Location;
import com.crio.stayease.exchange.HotelRequest;
import com.crio.stayease.repository.HotelRepository;
import com.crio.stayease.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the {@link HotelService} interface for managing hotel-related operations.
 *
 * <p>This service provides functionality for creating, updating, retrieving, and deleting hotel records.
 * It interacts with the {@link HotelRepository} to persist and manage hotel data.</p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@code HotelRepository} - Repository for hotel management.</li>
 * </ul>
 *
 * @see com.crio.stayease.services.HotelService
 * @see com.crio.stayease.entity.Hotel
 * @see com.crio.stayease.entity.Location
 */
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;

    /**
     * Creates a new hotel based on the provided {@link HotelRequest}.
     *
     * <p>This method constructs a {@link Location} object from the provided request data and creates a new
     * {@link Hotel} instance with the specified details. The hotel is then saved to the repository.</p>
     *
     * @param hotelRequest the request object containing details for the new hotel
     * @return the created {@link Hotel} object
     */
    public Hotel create(HotelRequest hotelRequest) {
        Location location = new Location();
        location.setAddress(hotelRequest.getAddress());
        location.setLatitude(hotelRequest.getLatitude());
        location.setLongitude(hotelRequest.getLongitude());

        Hotel hotel = Hotel.builder()
                .name(hotelRequest.getName())
                .description(hotelRequest.getDescription())
                .noOfRooms(hotelRequest.getNoOfRooms().orElse(0))
                .location(location)
                .build();

        return hotelRepository.save(hotel);
    }

    /**
     * Updates an existing hotel with the provided details.
     *
     * <p>This method updates the properties of an existing hotel based on the data in the provided map.
     * It also updates the associated {@link Location} if location details are included in the request.
     * The updated hotel is then saved to the repository.</p>
     *
     * @param id the ID of the hotel to update
     * @param hotelRequest a map containing the properties to update
     * @return the updated {@link Hotel} object
     * @throws ResponseStatusException if the hotel is not found
     */
    @Override
    public Hotel update(Long id, Map<String, Object> hotelRequest) {

        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));

        if (hotelRequest.get("name") != null) {
            existingHotel.setName((String) hotelRequest.get("name"));
        }
        if (hotelRequest.get("description") != null) {
            existingHotel.setDescription((String) hotelRequest.get("description"));
        }
        if (hotelRequest.get("noOfRooms") != null) {
            existingHotel.setNoOfRooms((int) hotelRequest.get("noOfRooms"));
        }

        // Update location
        Location existingLocation = existingHotel.getLocation();
        if (existingLocation == null) {
            existingLocation = new Location();
            existingHotel.setLocation(existingLocation);
        }
        if (hotelRequest.get("address") != null) {
            existingLocation.setAddress((String) hotelRequest.get("address"));
        }
        if (hotelRequest.get("latitude") != null) {
            existingLocation.setLatitude((Double) hotelRequest.get("latitude"));
        }
        if (hotelRequest.get("longitude") != null) {
            existingLocation.setLongitude((Double) hotelRequest.get("longitude"));
        }

        return hotelRepository.save(existingHotel);
    }

    /**
     * Retrieves a list of all hotels.
     *
     * @return a list of {@link Hotel} objects representing all hotels in the system
     */
    @Override
    public List<Hotel> get() {
        return hotelRepository.findAll();
    }

    /**
     * Retrieves a hotel by its ID.
     *
     * @param id the ID of the hotel to retrieve
     * @return an {@link Optional} containing the {@link Hotel} object if found
     * @throws ResponseStatusException if the hotel is not found
     */
    @Override
    public Optional<Hotel> get(Long id) {
        Optional<Hotel> o = hotelRepository.findById(id);
        if (o.isPresent()) {
            return o;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        }
    }

    /**
     * Deletes a hotel by its ID.
     *
     * @param id the ID of the hotel to delete
     * @return {@code true} if the hotel was successfully deleted, {@code false} otherwise
     * @throws ResponseStatusException if the hotel is not found
     */
    @Override
    public boolean delete(Long id) {
        Optional<Hotel> o = hotelRepository.findById(id);
        if (o.isPresent()) {
            hotelRepository.deleteById(id);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        }
    }
}
