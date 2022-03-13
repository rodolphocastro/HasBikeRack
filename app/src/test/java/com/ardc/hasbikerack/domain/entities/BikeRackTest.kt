@file:Suppress("ClassName")

package com.ardc.hasbikerack.domain.entities

import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
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
        val got = BikeRack(bogusName, BikeRack.RackSpotsState(bogusQtyOfSpots), bogusCoords)

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
        val subject = BikeRack(bogusName, BikeRack.RackSpotsState(qtyOfSpots), bogusCoords)
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
        val subject =
            BikeRack(bogusName, BikeRack.RackSpotsState(qtyOfSpots, takenSpots), bogusCoords)
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
            BikeRack(bogusName, BikeRack.RackSpotsState(bogusQtyOfSpots), bogusCoords)
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
            BikeRack(
                bogusName,
                BikeRack.RackSpotsState(bogusQtyOfSpots, bogusQtyOfSpots),
                bogusCoords
            )
                .parkBike()
        }

        // Act

        // Assert
        assertThrows(BikeRack.DomainException::class.java, act)
    }

    @Test
    @Parameters(
        "1, 0",
        "2, 2",
        "200, 0"
    )
    fun `Given a total number of spots and a number of taken spots, when I construct, then a Rack Spots is created`(
        totalSpots: Int,
        takenSpots: Int
    ) {
        // Arrange
        val expectedAvailableSpots = totalSpots - takenSpots

        // Act
        val subject = BikeRack.RackSpotsState(totalSpots, takenSpots)
        val (gotSpots, gotTakenSpots) = subject
        val gotAvailableSpots: Int = subject.availableSpots

        // Assert
        assertEquals(totalSpots, gotSpots)
        assertEquals(takenSpots, gotTakenSpots)
        assertEquals(expectedAvailableSpots, gotAvailableSpots)
    }

    @Test
    @Parameters(
        "0, 1",
        "1000, 10000",
        "1, 2"
    )
    fun `Given a total number of spots and a number of taken spots greater than the total number, when I construct, then an Exception happens`(
        totalSpots: Int,
        takenSpots: Int
    ) {
        // Arrange
        val act: () -> Unit = {
            BikeRack.RackSpotsState(totalSpots, takenSpots)
        }

        // Act

        // Assert
        assertThrows(BikeRack.DomainException::class.java, act)
    }

    @Test
    @Parameters(
        "1, 0",
        "2, 1",
        "200, 150"
    )
    fun `Given a spot with taken spots less than available spots, when I increment a taken spot, then there's one less available spot`(
        totalSpots: Int,
        takenSpots: Int
    ) {
        // Arrange
        val subject = BikeRack.RackSpotsState(totalSpots, takenSpots)
        val expected = (totalSpots - takenSpots) - 1

        // Act
        val got: Int = (subject park 1).availableSpots

        // Assert
        assertEquals(expected, got)
    }

    @Test
    @Parameters(
        "1, 1",
        "2, 1",
        "200, 150"
    )
    fun `Given a spot with taken spots greater than zero, when I decrement a taken spot, then there's one more available spot`(
        totalSpots: Int,
        takenSpots: Int
    ) {
        // Arrange
        val subject = BikeRack.RackSpotsState(totalSpots, takenSpots)
        val expected = (totalSpots - takenSpots) + 1

        // Act
        val got: Int = (subject remove 1).availableSpots

        // Assert
        assertEquals(expected, got)
    }
}