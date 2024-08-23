package com.crio.stayease.repository;

import com.crio.stayease.entity.Hotel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The {@code HotelRepository} interface extends {@code JpaRepository} to
 * provide CRUD operations for {@code Hotel} entities.
 *
 * <p>
 * This repository interface facilitates interaction with the database for
 * {@code Hotel} entities and includes additional
 * query methods to retrieve hotels based on specific criteria.
 * </p>
 *
 * <p>
 * <b>Inheritance:</b>
 * </p>
 * <ul>
 * <li>{@code JpaRepository<Hotel, Long>} - Provides standard CRUD operations,
 * sorting, and pagination support for the {@code Hotel} entity.</li>
 * </ul>
 *
 * <p>
 * <b>Custom Query Methods:</b>
 * </p>
 * <ul>
 * <li>{@code List<Hotel> findHotelsByNoOfRoomsGreaterThanEqual(int roomAvailable)}
 * - Retrieves a list of hotels where the number of rooms is greater than or
 * equal to the specified value. This method
 * is useful for querying hotels based on room availability.</li>
 * <li>{@code Optional<Hotel> findById(Long id)}
 * - Finds a hotel by its ID and acquires a pessimistic write lock on the hotel
 * record to handle concurrent modifications.</li>
 * </ul>
 *
 * <p>
 * <b>Usage:</b>
 * </p>
 * <p>
 * This repository interface should be used to perform CRUD operations and
 * custom queries related to hotels. It can be
 * injected into services or controllers where hotel data needs to be accessed
 * or manipulated.
 * </p>
 *
 * <pre>{@code
 * @Autowired
 * private HotelRepository hotelRepository;
 *
 * // Example usage:
 * List<Hotel> availableHotels = hotelRepository.findHotelsByNoOfRoomsGreaterThanEqual(10);
 * Optional<Hotel> hotel = hotelRepository.findById(1L);
 * }</pre>
 *
 * @see com.crio.stayease.entity.Hotel
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    /**
     * Finds all hotels with a number of rooms greater than or equal to the
     * specified value.
     *
     * @param roomAvailable the minimum number of rooms a hotel must have to be
     *                      included in the result
     * @return a list of hotels with at least the specified number of rooms
     */
    List<Hotel> findHotelsByNoOfRoomsGreaterThanEqual(int roomAvailable);

}
