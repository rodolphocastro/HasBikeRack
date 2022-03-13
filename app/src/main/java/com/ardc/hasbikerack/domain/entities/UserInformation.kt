package com.ardc.hasbikerack.domain.entities


/**
 * A default name for users that decide not to use their real names.
 */
const val unauthorizedName = "Cyclist"

/**
 * DTO for User's Information.
 */
data class UserInformation(val name: String) {
    companion object {
        /**
         * A user that has decided not to share its name, somehow.
         */
        val Cyclist = UserInformation(unauthorizedName)
    }
}