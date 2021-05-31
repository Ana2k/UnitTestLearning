package com.example.unittest2

import android.text.Editable
import android.text.TextWatcher
import java.util.regex.Pattern


open class EmailValidator : TextWatcher {
    /**
     * An Email format validator for {@link android.widget.EditText}.
     */
    /**
     * Email validation pattern.
     */
    val EMAIL_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    var mIsValid: Boolean? = null

    fun isValid(): Boolean {
        return mIsValid == true
    }

    /**
     * Validates if the given input is a valid email address.
     *
     * @param email        The email to validate.
     * @return {@code true} if the input is a valid email. {@code false} otherwise.
     */

    open fun isValidEmail(email: CharSequence?): Boolean {
        return email != null && EMAIL_PATTERN.matcher(email).matches()
    }


    override fun afterTextChanged(editableText: Editable?) {
        mIsValid = isValidEmail(editableText)
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { /*No-op*/
    }


    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {/*No-Op*/
    }
}