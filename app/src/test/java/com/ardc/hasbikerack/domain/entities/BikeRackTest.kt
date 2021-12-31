@file:Suppress("ClassName")

package com.ardc.hasbikerack.domain.entities

import org.junit.Assert.*
import org.junit.Test

class `Bike Rack tests` {
    private val bogusName = "The bad place"
    private val bogusQtyOfSpots = 42
    private val bogusCoords = Coordinates(1.0, 1.0)

    @Test
    fun `Given a name, a number of spots and a set of coordinates, when constructed, then a Rack is created`() {
        // Arrange

        // Act
        val got = BikeRack(bogusName, bogusQtyOfSpots, bogusCoords)

        // Assert
        assertEquals(bogusName, got.name)
        assertEquals(bogusQtyOfSpots, got.quantityOfSpots)
        assertEquals(bogusCoords, got.coords)
    }
}