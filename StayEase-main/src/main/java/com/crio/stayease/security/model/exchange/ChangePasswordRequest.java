package com.crio.stayease.security.model.exchange;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * Represents a request to change a user's password.
 *
 * <p>This class encapsulates the information required for a user to request a password change.
 * It includes the current password, the new password, and a confirmation of the new password.</p>
 *
 * <p>The {@code ChangePasswordRequest} class contains the following fields:
 * <ul>
 *     <li>{@code currentPassword} - The user's current password, used to verify the user's identity
 *         before allowing the change.</li>
 *     <li>{@code newPassword} - The new password that the user wishes to set. This password should
 *         meet the system's security requirements.</li>
 *     <li>{@code confirmationPassword} - A confirmation of the new password. This field is used to
 *         ensure that the user has correctly entered the new password.</li>
 * </ul>
 * </p>
 *
 * <p>Note: The password fields are usually validated to ensure they meet the necessary security
 * criteria, such as minimum length and complexity.</p>
 */
@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    /**
     * The user's current password, used to verify the user's identity before allowing the change.
     */
    private String currentPassword;

    /**
     * The new password that the user wishes to set. This password should meet the system's security
     * requirements.
     */
    private String newPassword;

    /**
     * A confirmation of the new password. This field is used to ensure that the user has correctly
     * entered the new password.
     */
    private String confirmationPassword;
}
