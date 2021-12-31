@file:Suppress("ClassName")

package com.ardc.hasbikerack.domain.entities

import junitparams.Parameters
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(junitparams.JUnitParamsRunner::class)
class `Rack Spots tests` {

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
        val subject = BikeRack.RackSpots(totalSpots, takenSpots)
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
            BikeRack.RackSpots(totalSpots, takenSpots)
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
        val subject = BikeRack.RackSpots(totalSpots, takenSpots)
        val expected = takenSpots + 1

        // Act
        val got: Int = subject.availableSpots

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
        val subject = BikeRack.RackSpots(totalSpots, takenSpots)
        val expected = takenSpots + 1

        // Act
        val got: Int = subject.availableSpots

        // Assert
        assertEquals(expected, got)
    }

}