package com.ardc.hasbikerack.domain.entities

import org.junit.Assert.assertEquals
import org.junit.Test

class UserInformationTest {

    @Test
    fun `A Cyclist should have the default unauthorized name`() {
        // Arrange
        val subject = UserInformation.Cyclist

        // Act
        val got = subject.name

        // Assert
        assertEquals(unauthorizedName, got)
    }
}