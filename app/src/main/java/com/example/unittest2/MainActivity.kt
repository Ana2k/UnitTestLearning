package com.example.unittest2

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


/**
 * An {@link Activity} that represents an input form page where the user can provide his name, date
 * of birth and email address. The personal information can be saved to {@link SharedPreferences}
 * by clicking button
 * **/

class MainActivity : AppCompatActivity() {

    //DECLARATION

    // Logger for this class.
    private val TAG: String = "MainActivity"

    // The helper that manages writing to SharedPreferences.
    private var mSharedPreferencesHelper: SharedPreferencesHelper? = null

    // The input field where the user enters his name.
    private var mNameText: EditText? = null

    // The date picker where the user enters his date of birth.
    private var mDobPicker: DatePicker? = null

    // The input field where the user enters his email.
    private var mEmailText: EditText? = null

    // The validator for the email input field.
    private var mEmailValidator: EmailValidator? = null


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Shortcuts to input feilds
        mNameText = findViewById(R.id.userNameInput)
        mDobPicker = findViewById(R.id.dateOfBirthInput)
        mEmailText = findViewById(R.id.emailInput)

        //Setup field validators
        mEmailValidator = EmailValidator()
        mEmailText.run { this?.addTextChangedListener(mEmailValidator) }

        // Instantiate a SharedPreferencesHelper.
        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mSharedPreferencesHelper = SharedPreferencesHelper(sharedPreferences)

        // Fill input fields from data retrieved from the SharedPreferences.
        populateUi()
    }

    private fun populateUi() {
        val sharedPreferenceEntry: SharedPreferenceEntry? =
            mSharedPreferencesHelper?.getPersonalInfo()

        //calling assingning
        //basically connecting the components together
        //sharedprefEntry and main that is.
        mNameText?.setText(sharedPreferenceEntry?.getName())
        var dateOfBirth: Calendar? = sharedPreferenceEntry?.getDateOfBirth()
        if (dateOfBirth != null) {
            mDobPicker?.init(
              dateOfBirth.get(Calendar.YEAR), dateOfBirth.get(Calendar.MONTH),
              dateOfBirth.get(Calendar.DAY_OF_MONTH), null
            )
        }
        if (sharedPreferenceEntry != null) {
            mEmailText?.setText(sharedPreferenceEntry.getEmail())
        }
    }

    /**
     * Called when the "Save" button is clicked.
     */
    fun onSaveClick(view: View?) {
        // Don't save if the fields do not validate.
        var check = mEmailValidator?.isValid()
        if (check != null) {
            if (!check) {
                //HOW DO I STOP IT FROM GIVING AN ERROR??
                //Added custom null operator
                mEmailText?.setError("InvalidEmail")
                Log.w(TAG, "Not saving personal info: InValid email")
                return
            }
        }
        // Get the text from the input fields.
        var name = mNameText?.text.toString()
        val dateOfBirth = Calendar.getInstance()

        //Calendar date setting is left
        //DONE(*)
        mDobPicker?.let { dateOfBirth.set(it.getYear(), it.getMonth(), it.getDayOfMonth()) }

        var email = mEmailText?.text.toString()

        // Create a Setting model class to persist.
        var sharedPreferenceEntry = SharedPreferenceEntry(name, dateOfBirth, email)

        // Persist the personal information.
        val isSuccess = mSharedPreferencesHelper?.savePersonalInfo(sharedPreferenceEntry)
        if (isSuccess == true) {
            Toast.makeText(this, "Personal information saved", Toast.LENGTH_LONG).show()
            Log.i(TAG, "Personal information saved")
        } else {
            Log.e(TAG, "Failed to write personal information to SharedPreferences")
        }
    }

    /**
     * Called when the "Revert" button is clicked.
     */
    fun onRevertClick(view: View?) {
        populateUi()
        Toast.makeText(this, "Personal information reverted", Toast.LENGTH_LONG).show()
        Log.i(TAG, "Personal information reverted")
    }

}


