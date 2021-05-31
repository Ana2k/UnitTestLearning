package com.example.unittest2

import java.util.*

class SharedPreferenceEntry(name: String?, dateOfBirth: Calendar, email: String?) {
    //Entries
    //Name,Dob and email members
    private var mName: String?= null
    private var mDateOfBirth: Calendar?= null
    private var mEmail: String?=null

    init{
        mName = name
        mDateOfBirth = dateOfBirth
        mEmail = email
    }

    fun getName(): String? {
        return mName
    }

    fun getDateOfBirth(): Calendar? {
        return mDateOfBirth
    }

    fun getEmail(): String? {
        return mEmail
    }

}