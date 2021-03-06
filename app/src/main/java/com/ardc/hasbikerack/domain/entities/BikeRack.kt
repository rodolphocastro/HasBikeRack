package com.ardc.hasbikerack.domain.entities

/**
 * A set of Coordinates in a 2d axis.
 */
data class Coordinates(val x: Double, val y: Double) {

}

/**
 * A Bicycle Rack where one can park his/hers bicycle.
 * @param name The name of this rack
 * @param spots The number of spots this rack has
 * @param coords The latitude/longitude for this rack
 */
data class BikeRack(
    val name: String,
    val spots: RackSpotsState,
    val coords: Coordinates,
) {

    /**
     * The spots available on a Rack.
     * @param totalSpots the total amount of spots
     * @param takenSpots the spots already taken
     */
    data class RackSpotsState(val totalSpots: Int, val takenSpots: Int = 0) {
        init {
            when {
                totalSpots < 0 ->
                    throw DomainException("A rack's total spots must be greater than or equal to zero")
                takenSpots < 0 ->
                    throw DomainException("A rack's taken spots must be greater than or equal to zero")
                totalSpots < takenSpots ->
                    throw DomainException("A rack's taken spots must always be equal to or lesser than its total spots")
            }
        }

        /**
         * parks a number of bicycles into a spot.
         * @throws DomainException
         */
        @Throws(DomainException::class)
        infix fun park(amount: Int): RackSpotsState {
            return copy(takenSpots = this.takenSpots + amount)
        }

        /**
         * removes a number of bicycles from a spot.
         */
        @Throws(DomainException::class)
        infix fun remove(amount: Int): RackSpotsState {
            return copy(takenSpots = this.takenSpots - amount)
        }

        /**
         * the amount of spots available for parking
         */
        val availableSpots: Int
            get() = totalSpots - takenSpots
    }

    /**
     * Parks a single bike into the rack.
     * @throws DomainException
     */
    @Throws(DomainException::class)
    fun parkBike(): BikeRack {
        return copy(spots = (this.spots park 1))
    }

    /**
     * Unparks a single bike from the rack.
     * @throws DomainException
     */
    @Throws(DomainException::class)
    fun takeBike(): BikeRack {
        return copy(spots = (this.spots remove 1))
    }

    /**
     * The amount of available spots.
     */
    val availableSpots: Int
        get() = spots.availableSpots

    /**
     * This Exception means that a domain rule for Bike Racks has been broken.
     */
    class DomainException(message: String?) : Exception(message)
}