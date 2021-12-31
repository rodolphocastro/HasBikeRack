@file:Suppress("ClassName")

package com.ardc.hasbikerack.domain.entities

import org.junit.Assert.*
import org.junit.Test

class `Bike Rack tests` {
    private val bogusName = "The bad place"
    private val bogusQtyOfSpots = 42

    @Test
    fun `Given a name and a number of spots, when constructed, then a Rack is created`() {
        // Arrange

        // Act
        val got = BikeRack(bogusName, bogusQtyOfSpots)

        // Assert
        assertEquals(bogusName, got.name)
        assertEquals(bogusQtyOfSpots, got.quantityOfSpots)
    }
}