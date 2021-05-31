package com.example.unittest2

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EmailValidatorTest {

    var emailValidator:EmailValidator = EmailValidator()
    //Object creation for required class

    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue(){
        assertTrue(emailValidator.isValidEmail("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(emailValidator.isValidEmail("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(emailValidator.isValidEmail("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(emailValidator.isValidEmail("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(emailValidator.isValidEmail("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(emailValidator.isValidEmail(""))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse(){
        assertFalse(emailValidator.isValidEmail(null))
    }
}