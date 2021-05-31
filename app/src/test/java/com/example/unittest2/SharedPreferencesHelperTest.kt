package com.example.unittest2

import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner

/**
 * Unit tests for the {@link SharedPreferencesHelper} that mocks {@link SharedPreferences}.
 */
@RunWith(
    MockitoJUnitRunner.class)
            open class SharedHelperTest{
        private val TEST_NAME = "Test name"
        private val TEST_EMAIL = "test@email.com"
        private val TEST_DATE_OF_BIRTH: Calendar = Calendar.getInstance()
        static
        {
            TEST_DATE_OF_BIRTH.set(1980, 1, 1)
        }

    }