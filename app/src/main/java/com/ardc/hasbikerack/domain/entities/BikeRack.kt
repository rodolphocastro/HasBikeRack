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
 */
data class BikeRack(
    val name: String,
    val quantityOfSpots: Int,
    val coords: Coordinates,
    val quantityOfTakenSpots: Int = 0
) {

    fun parkBike(): BikeRack {
        return copy(quantityOfTakenSpots = this.quantityOfTakenSpots + 1)
    }

    fun takeBike(): BikeRack {
        return copy(quantityOfTakenSpots = this.quantityOfTakenSpots - 1)
    }

    val availableSpots: Int
        get() = quantityOfSpots - quantityOfTakenSpots
}