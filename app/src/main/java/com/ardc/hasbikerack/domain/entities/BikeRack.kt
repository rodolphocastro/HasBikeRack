package com.ardc.hasbikerack.domain.entities

/**
 * A set of Coordinates in a 2d axis.
 */
data class Coordinates(val x: Double, val y: Double) {

}

/**
 * A Bicycle Rack where one can park his/hers bicycle.
 * @param name The name of this rack
 * @param quantityOfSpots The number of spots this rack has (aka: how many bikes can park here)
 * @param coords The latitude/longitude for this rack
 * @param quantityOfTakenSpots The amount of spots taken, defaults to 0
 */
data class BikeRack(
    val name: String,
    val quantityOfSpots: Int,
    val coords: Coordinates,
    val quantityOfTakenSpots: Int = 0
) {

    /**
     * The spots available on a Rack.
     */
    data class RackSpots(val totalSpots: Int, val takenSpots: Int = 0) {

    }

    /**
     * Parks a single bike into the rack.
     * @throws DomainException
     */
    @Throws(DomainException::class)
    fun parkBike(): BikeRack {
        val result = quantityOfTakenSpots + 1
        if (result > quantityOfSpots)
            throw DomainException("A rack's taken spots must always be equal or lesser than it's total spots")

        return copy(quantityOfTakenSpots = result)
    }

    /**
     * Unparks a single bike from the rack.
     * @throws DomainException
     */
    @Throws(DomainException::class)
    fun takeBike(): BikeRack {
        val result = quantityOfTakenSpots - 1
        if (result < 0)
            throw DomainException("A rack's taken spots must always be equal or greater than zero")

        return copy(quantityOfTakenSpots = result)
    }

    /**
     * The amount of available spots.
     */
    val availableSpots: Int
        get() = quantityOfSpots - quantityOfTakenSpots

    /**
     * This Exception means that a domain rule for Bike Racks has been broken.
     */
    class DomainException(message: String?) : Exception(message)
}