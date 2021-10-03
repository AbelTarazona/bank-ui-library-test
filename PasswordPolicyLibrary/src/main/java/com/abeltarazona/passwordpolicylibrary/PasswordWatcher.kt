package com.abeltarazona.passwordpolicylibrary

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import com.abeltarazona.passwordpolicylibrary.PasswordUtils.Companion.hasAccentMark
import com.abeltarazona.passwordpolicylibrary.PasswordUtils.Companion.hasLowerCase
import com.abeltarazona.passwordpolicylibrary.PasswordUtils.Companion.hasMinimumLength
import com.abeltarazona.passwordpolicylibrary.PasswordUtils.Companion.hasNumber
import com.abeltarazona.passwordpolicylibrary.PasswordUtils.Companion.hasSpecialChar
import com.abeltarazona.passwordpolicylibrary.PasswordUtils.Companion.hasUpperCase

/**
 * Created by AbelTarazona on 2/10/2021
 */
class PasswordWatcher  : TextWatcher {

    var strengthLevel: MutableLiveData<StrengthLevel> = MutableLiveData()
    var strengthColor: MutableLiveData<Int> = MutableLiveData()

    var lowerUpperCase = MutableLiveData(0)
    var number = MutableLiveData(0)
    var specialChar = MutableLiveData(0)
    var minimumLength = MutableLiveData(0)
    var hasNotAccentMark = MutableLiveData(0)

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (char != null) {
            lowerUpperCase.value = if (hasLowerCase(char) and hasUpperCase(char)) 1 else 0
            number.value = if (hasNumber(char)) 1 else 0
            specialChar.value = if (hasSpecialChar(char)) 1 else 0
            minimumLength.value = if (hasMinimumLength(char)) 1 else 0
            hasNotAccentMark.value = if (!hasAccentMark(char)) 1 else 0
            calculateStrength(char)
        }
    }

    private fun calculateStrength(password: CharSequence) {
        when (password.length) {
            in 0..7 -> {
                if (lowerUpperCase.value == 1 || number.value == 1 || specialChar.value == 1 || minimumLength.value == 1 || hasNotAccentMark.value == 1) {
                    strengthColor.value = R.color.passwordLibraryWeak
                    strengthLevel.value = StrengthLevel.WEAK
                }
            }
            in 8..Int.MAX_VALUE -> {
                if (lowerUpperCase.value == 1 && number.value == 1 && specialChar.value == 1 && minimumLength.value == 1 && hasNotAccentMark.value == 1) {
                    strengthColor.value = R.color.passwordLibraryStrong
                    strengthLevel.value = StrengthLevel.STRONG
                } else {
                    strengthColor.value = R.color.passwordLibraryWeak
                    strengthLevel.value = StrengthLevel.WEAK
                }
            }
        }
    }
}