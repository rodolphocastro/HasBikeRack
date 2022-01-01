@file:Suppress("ClassName")

package com.ardc.hasbikerack.domain.entities

import junitparams.Parameters
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(junitparams.JUnitParamsRunner::class)
class `Bike Rack tests` {
    private val bogusName = "The bad place"
    private val bogusQtyOfSpots = 42
    private val bogusCoords = Coordinates(1.0, 1.0)

    @Test
    fun `Given a name, a number of spots, a number of taken spots and a set of coordinates, when constructed, then a Rack is created`() {
        // Arrange

        // Act
        val got = BikeRack(bogusName, BikeRack.RackSpots(bogusQtyOfSpots), bogusCoords)

        // Assert
        assertEquals(bogusName, got.name)
        assertEquals(bogusQtyOfSpots, got.spots.totalSpots)
        assertEquals(bogusCoords, got.coords)
        assertEquals(bogusQtyOfSpots, got.availableSpots)   // Because available = qty - taken
    }

    @Test
    @Parameters(
        "20",
        "1",
        "100"
    )
    fun `Given a rack, when I park a bike, then the available spots are reduced by one`(
        qtyOfSpots: Int
    ) {
        // Arrange
        val subject = BikeRack(bogusName, BikeRack.RackSpots(qtyOfSpots), bogusCoords)
        val expected = qtyOfSpots - 1

        // Act
        val got: BikeRack = subject.parkBike()

        // Assert
        assertEquals(expected, got.availableSpots)
    }

    @Test
    @Parameters(
        "20, 19",
        "10, 1",
        "1, 1",
        "2000, 1999"
    )
    fun `Given a rack, when I remove a bike, then the available spots are increased by one`(
        qtyOfSpots: Int,
        takenSpots: Int
    ) {
        // Arrange
        val subject = BikeRack(bogusName, BikeRack.RackSpots(qtyOfSpots, takenSpots), bogusCoords)
        val expected = (qtyOfSpots - takenSpots) + 1

        // Act
        val got: BikeRack = subject.takeBike()

        // Assert
        assertEquals(expected, got.availableSpots)
    }

    @Test
    fun `Given a rack without any parked bikes, when I remove a bike, then an Exception is throw`() {
        // Arrange
        val act: () -> Unit = {
            BikeRack(bogusName, BikeRack.RackSpots(bogusQtyOfSpots), bogusCoords)
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
            BikeRack(bogusName, BikeRack.RackSpots(bogusQtyOfSpots, bogusQtyOfSpots), bogusCoords)
                .parkBike()
        }

        // Act

        // Assert
        assertThrows(BikeRack.DomainException::class.java, act)
    }

}