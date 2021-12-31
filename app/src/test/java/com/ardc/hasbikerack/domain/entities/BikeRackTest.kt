@file:Suppress("ClassName")

package com.ardc.hasbikerack.domain.entities

import org.junit.Assert.*
import org.junit.Test

class `Bike Rack tests` {
    private val bogusName = "The bad place"
    private val bogusQtyOfSpots = 42
    private val bogusCoords = Coordinates(1.0, 1.0)

    @Test
    fun `Given a name, a number of spots, a number of taken spots and a set of coordinates, when constructed, then a Rack is created`() {
        // Arrange

        // Act
        val got = BikeRack(bogusName, bogusQtyOfSpots, bogusCoords)

        // Assert
        assertEquals(bogusName, got.name)
        assertEquals(bogusQtyOfSpots, got.quantityOfSpots)
        assertEquals(bogusCoords, got.coords)
        assertEquals(bogusQtyOfSpots, got.availableSpots)   // Because available = qty - taken
    }

    @Test
    fun `Given a rack, when I park a bike, then the available spots are reduced by one`() {
        // Arrange
        val takenSpots = 0
        val subject = BikeRack(bogusName, bogusQtyOfSpots, bogusCoords, takenSpots)
        val expected = bogusQtyOfSpots - 1

        // Act
        val got: BikeRack = subject.parkBike()

        // Assert
        assertEquals(expected, got.availableSpots)
    }

    @Test
    fun `Given a rack, when I remove a bike, then the available spots are increased by one`() {
        // Arrange
        val takenSpots = bogusQtyOfSpots
        val subject = BikeRack(bogusName, bogusQtyOfSpots, bogusCoords, takenSpots)
        val expected = (bogusQtyOfSpots - takenSpots) + 1

        // Act
        val got: BikeRack = subject.takeBike()

        // Assert
        assertEquals(expected, got.availableSpots)
    }

    @Test
    fun `Given a rack without any parked bikes, when I remove a bike, then an Exception is throw`() {
        // Arrange
        val act: () -> Unit = {
            BikeRack(bogusName, bogusQtyOfSpots, bogusCoords, 0)
                .takeBike()
        }

        // Act

        // Assert
        assertThrows(BikeRack.DomainException::class.java, act)
    }

    @Test
    fun `Given a rack without available spots, when I park a bike, then an Exception is throw`() {
        // Arrange
        val act: () -> Unit = {
            BikeRack(bogusName, bogusQtyOfSpots, bogusCoords, bogusQtyOfSpots)
                .parkBike()
        }

        // Act

        // Assert
        assertThrows(BikeRack.DomainException::class.java, act)
    }

}