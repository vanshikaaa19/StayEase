package com.crio.stayease.model;

/**
 * The {@code BookingStatus} enum represents the various statuses that a booking can have in the stay ease system.
 *
 * <p>This enumeration is used to track and manage the state of a booking throughout its lifecycle. The statuses are as follows:</p>
 * <ul>
 *   <li>{@code BOOKED} - Indicates that the booking has been successfully made and confirmed.</li>
 *   <li>{@code CANCELLED} - Indicates that the booking has been cancelled by the user or the system.</li>
 *   <li>{@code PENDING} - Indicates that the booking is pending and awaits confirmation or further action.</li>
 *   <li>{@code FAILED} - Indicates that the booking process has failed due to an error or issue.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>This enum is used to set and check the status of bookings in various parts of the application. For example, it can be used
 * to filter bookings based on their status or to update the status of a booking during various operations.</p>
 *
 * <pre>{@code
 * // Example usage:
 * BookingStatus status = BookingStatus.BOOKED;
 * if (status == BookingStatus.CANCELLED) {
 *     // Handle cancelled booking
 * }
 * }</pre>
 */
public enum BookingStatus {
    /**
     * The booking has been successfully made and confirmed.
     */
    BOOKED,

    /**
     * The booking has been cancelled by the user or the system.
     */
    CANCELLED,

    /**
     * The booking is pending and awaits confirmation or further action.
     */
    PENDING,

    /**
     * The booking process has failed due to an error or issue.
     */
    FAILED
}
