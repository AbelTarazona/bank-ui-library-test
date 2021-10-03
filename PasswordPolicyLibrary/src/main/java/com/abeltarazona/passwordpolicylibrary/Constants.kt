package com.abeltarazona.passwordpolicylibrary

/**
 * Created by AbelTarazona on 2/10/2021
 */
object Constants {
    const val SPECIAL_CHARACTERS_REGEX = "\"@#$%^&()?!.,:;_/*=+-"
    const val SPECIAL_CHARACTERS_VALIDATOR = "[$SPECIAL_CHARACTERS_REGEX]"
    const val ALPHANUMERIC_VALIDATOR = "^[a-zA-ZñÑ0-9$SPECIAL_CHARACTERS_REGEX]*$"
    const val ALPHANUMERIC_NEGATED_VALIDATOR = "[^a-zA-ZñÑ0-9$SPECIAL_CHARACTERS_REGEX]"
}