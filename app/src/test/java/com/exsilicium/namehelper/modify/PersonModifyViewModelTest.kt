package com.exsilicium.namehelper.modify

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class PersonModifyViewModelTest {

    @Test
    fun inputIsInvalid() {
        assertFalse(
            "Expected empty name strings to be invalid.",
            PersonModifyViewModel.isInputValid("", "")
        )
    }

    @Test
    fun inputIsValid() {
        assertTrue(
            "Expected non-empty name strings to be valid.",
            PersonModifyViewModel.isInputValid("John", "Doe")
        )
    }
}
