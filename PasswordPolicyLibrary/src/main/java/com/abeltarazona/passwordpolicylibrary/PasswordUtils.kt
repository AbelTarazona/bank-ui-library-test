package com.abeltarazona.passwordpolicylibrary

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by AbelTarazona on 2/10/2021
 */
class PasswordUtils {
    companion object {
        fun hasMinimumLength(text: CharSequence): Boolean {
            val pattern: Pattern = Pattern.compile("[\\S]{8,20}")
            val hasMinimumLength: Matcher = pattern.matcher(text)
            return hasMinimumLength.find()
        }

        fun hasLowerCase(text: CharSequence): Boolean {
            val pattern: Pattern = Pattern.compile("[a-z]")
            val hasLowerCase: Matcher = pattern.matcher(text)
            return hasLowerCase.find()
        }

        fun hasUpperCase(text: CharSequence): Boolean {
            val pattern: Pattern = Pattern.compile("[A-Z]")
            val hasUpperCase: Matcher = pattern.matcher(text)
            return hasUpperCase.find()
        }

        fun hasNumber(text: CharSequence): Boolean {
            val pattern: Pattern = Pattern.compile("[0-9]")
            val hasNumber: Matcher = pattern.matcher(text)
            return hasNumber.find()
        }

        fun hasSpecialChar(text: CharSequence): Boolean {
            val pattern: Pattern = Pattern.compile(Constants.SPECIAL_CHARACTERS_VALIDATOR)
            val hasSpecialChar: Matcher = pattern.matcher(text)
            return hasSpecialChar.find()
        }

        fun hasAccentMark(text: CharSequence): Boolean {
            val pattern: Pattern = Pattern.compile("[À-ÿ]")
            val hasAccentMark: Matcher = pattern.matcher(text)
            return hasAccentMark.find()
        }
    }
}