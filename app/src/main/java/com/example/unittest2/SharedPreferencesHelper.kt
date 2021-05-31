package com.example.unittest2

import android.content.SharedPreferences
import java.util.*

class SharedPreferencesHelper(sharedPreferences: SharedPreferences) {
    companion object {
        const val KEY_NAME = "keyname"
        const val KEY_DOB = "key_dob_millis";
        const val KEY_EMAIL = "key_email";
        // Keys for saving values in SharedPreferences
    }

    // The injected SharedPreferences implementation to use for persistence.
    var mSharedPreferences: SharedPreferences? = null

    /**
     * Constructor with dependency injection.
     *
     * @param sharedPreferences The [SharedPreferences] that will be used in this DAO.
     */
    init {
        mSharedPreferences = sharedPreferences
    }

    /**
     * Saves the given {@link SharedPreferenceEntry} that contains the user's settings to
     * {@link SharedPreferences}.
     *
     * @param sharedPreferenceEntry contains data to save to {@link SharedPreferences}.
     * @return {@code true} if writing to {@link SharedPreferences} succeeded. {@code false}
     *         otherwise.
     */
    fun savePersonalInfo(sharedPreferenceEntry: SharedPreferenceEntry): Boolean {
        // Start a SharedPreferences transaction.
        val editor = mSharedPreferences?.edit()
        editor?.putString(KEY_NAME, sharedPreferenceEntry.getName())
        sharedPreferenceEntry.getDateOfBirth()?.let { editor?.putLong(KEY_DOB, it.getTimeInMillis()) }
        editor?.putString(KEY_EMAIL, sharedPreferenceEntry.getEmail())
        // Commit changes to SharedPreferences.
        if (editor != null) {
            return editor.commit()
        }
        return false
    }

    /**
     * Retrieves the {@link SharedPreferenceEntry} containing the user's personal information from
     * {@link SharedPreferences}.
     *
     * @return the Retrieved {@link SharedPreferenceEntry}.
     */

    open fun getPersonalInfo(): SharedPreferenceEntry? {
        // Get data from the SharedPreferences.
        val name = mSharedPreferences?.getString(KEY_NAME, "")
        val dobMillis = mSharedPreferences?.getLong(KEY_DOB, Calendar.getInstance().timeInMillis)
        val dateOfBirth = Calendar.getInstance()
        if (dobMillis != null) {
            dateOfBirth.timeInMillis = dobMillis
        }
        val email = mSharedPreferences?.getString(KEY_EMAIL, "")

        // Create and fill a SharedPreferenceEntry model object.
        var SP = SharedPreferenceEntry(name, dateOfBirth, email)
        return SP
    }

}
