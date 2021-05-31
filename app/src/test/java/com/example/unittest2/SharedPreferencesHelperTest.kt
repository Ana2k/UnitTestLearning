package com.example.unittest2

import android.content.SharedPreferences
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import java.util.*


/**
 * Unit tests for the {@link SharedPreferencesHelper} that mocks {@link SharedPreferences}.
 */
@RunWith(MockitoJUnitRunner::class)
class SharedPreferencesHelperTest {
    //DEclarration
    private val TEST_NAME: String? = "Test name"
    private val TEST_EMAIL = "test@email.com"
    private val TEST_DATE_OF_BIRTH: Calendar = Calendar.getInstance()

    //TODO() static TEST_DATE_OF_BIRTH

    //creating obbjects for other classes
    private var mSharedPreferenceEntry: SharedPreferenceEntry? = null
    private var mMockSharedPreferencesHelper: SharedPreferencesHelper? = null
    private var mMockBrokenSharedPreferencesHelper: SharedPreferencesHelper? = null

    //MOCKS
    @Mock
    var mMockSharedPreferences: SharedPreferences? = null

    @Mock
    var mMockBrokenSharedPreferences: SharedPreferences? = null

    @Mock
    var mMockEditor: SharedPreferences.Editor? = null

    @Mock
    var mMockBrokenEditor: SharedPreferences.Editor? = null

    @Before
    fun initMocks() {
        // Create SharedPreferenceEntry to persist.
        mSharedPreferenceEntry = SharedPreferenceEntry(
            TEST_NAME, TEST_DATE_OF_BIRTH,
            TEST_EMAIL
        )
        // Create a mocked SharedPreferences.
        mMockSharedPreferencesHelper = createMockSharedPreference()
        // Create a mocked SharedPreferences that fails at saving data.
        mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference()
    }


    //TEST!
    @Test
    fun sharedPreferencesHelper_SaveAndReadPersonalInformation() {
        // Save the personal information to SharedPreferences
        val success = mSharedPreferenceEntry?.let {
            mMockSharedPreferencesHelper?.savePersonalInfo(it)
        }
        assertThat(
            "Checking that SharedPreferenceEntry.save... returns true",
            success, `is`(true)
        )
        // Read personal information from SharedPreferences
        val savedSharedPreferenceEntry = mMockSharedPreferencesHelper?.getPersonalInfo()
        // Make sure both written and retrieved personal information are equal.
        assertThat(
            "Checking that SharedPreferenceEntry.name has been persisted and read correctly",
            mSharedPreferenceEntry?.getName(),
            `is`(equalTo(savedSharedPreferenceEntry?.getName()))
        )
        assertThat(
            "Checking that SharedPreferenceEntry.dateOfBirth has been persisted and read "
                    + "correctly",
            mSharedPreferenceEntry?.getDateOfBirth(),
            `is`(equalTo(savedSharedPreferenceEntry?.getDateOfBirth()))
        )
        assertThat(
            ("Checking that SharedPreferenceEntry.email has been persisted and read "
                    + "correctly"),
            mSharedPreferenceEntry?.getEmail(),
            `is`(equalTo(savedSharedPreferenceEntry?.getEmail()))
        )
    }

    //TEST2
    @Test
    fun sharedPreferencesHelper_SavePersonalInformationFailed_ReturnsFalse() {
        // Read personal information from a broken SharedPreferencesHelper
        val success =
            mSharedPreferenceEntry?.let { mMockBrokenSharedPreferencesHelper?.savePersonalInfo(it) }
        assertThat(
            "Makes sure writing to a broken SharedPreferencesHelper returns false", success,
            `is`(false)
        )
    }

    /**
     * Creates a mocked SharedPreferences.
     */
    private fun createMockSharedPreference(): SharedPreferencesHelper? {
        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        `when`(
            mMockSharedPreferences?.getString(
                eq(SharedPreferencesHelper.KEY_NAME),
                anyString()
            )
        )
            .thenReturn(mSharedPreferenceEntry?.getName())
        `when`(
            mMockSharedPreferences?.getString(
                eq(SharedPreferencesHelper.KEY_EMAIL),
                anyString()
            )
        )
            .thenReturn(mSharedPreferenceEntry?.getEmail())
        `when`(mMockSharedPreferences?.getLong(eq(SharedPreferencesHelper.KEY_DOB), anyLong()))
            .thenReturn(mSharedPreferenceEntry?.getDateOfBirth()?.timeInMillis)
        // Mocking a successful commit.
        `when`(mMockEditor?.commit()).thenReturn(true)
        // Return the MockEditor when requesting it.
        `when`(mMockSharedPreferences?.edit()).thenReturn(mMockEditor)
        return mMockSharedPreferences?.let { SharedPreferencesHelper(it) }
    }


    /**
     * Creates a mocked SharedPreferences that fails when writing.
     */
    private fun createBrokenMockSharedPreference(): SharedPreferencesHelper? {
        // Mocking a commit that fails.
        `when`(mMockBrokenEditor?.commit()).thenReturn(false)
        // Return the broken MockEditor when requesting it.
        `when`(mMockBrokenSharedPreferences?.edit()).thenReturn(mMockBrokenEditor)
        return mMockBrokenSharedPreferences?.let { SharedPreferencesHelper(it) }
    }

}