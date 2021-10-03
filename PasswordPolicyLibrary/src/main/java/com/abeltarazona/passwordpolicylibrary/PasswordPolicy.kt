package com.abeltarazona.passwordpolicylibrary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.abeltarazona.passwordpolicylibrary.databinding.PasswordPolicyViewBinding
import it.sephiroth.android.library.xtooltip.ClosePolicy
import it.sephiroth.android.library.xtooltip.Tooltip

/**
 * Created by AbelTarazona on 2/10/2021
 */
class PasswordPolicy @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var binding: PasswordPolicyViewBinding

    private val passwordWatcher = PasswordWatcher()

    private var tooltip: Tooltip? = null

    lateinit var isCompletePasswordPolicy: (Boolean) -> Unit

    init {
        binding = PasswordPolicyViewBinding.inflate(LayoutInflater.from(context), this, true)
        binding.ibSpecialCharactersInformation.setOnClickListener {
            openTooltip()
        }
    }

    fun configure(passwordEditText: EditText) {
        passwordEditText.addTextChangedListener(passwordWatcher)
        handleWatcherChange()
    }

    fun setCallback(callback: (Boolean) -> Unit) {
        this.isCompletePasswordPolicy = callback
    }

    private fun handleWatcherChange() {
        passwordWatcher.strengthLevel.observe(context as LifecycleOwner, { strengthLevel ->
            displayStrength(strengthLevel)
        })

        passwordWatcher.strengthColor.observe(context as LifecycleOwner, { color ->
            binding.pbStrength.progressColor = ContextCompat.getColor(context, color)
        })

        passwordWatcher.lowerUpperCase.observe(context as LifecycleOwner, { hasLowerCase ->
            displayPasswordPolicy(hasLowerCase, binding.icLowUpperCase)
        })

        passwordWatcher.hasNotAccentMark.observe(context as LifecycleOwner, { hasNotAccentMark ->
            displayPasswordPolicy(hasNotAccentMark, binding.icAccentMarkCase)
        })

        passwordWatcher.number.observe(context as LifecycleOwner, { hasNumber ->
            displayPasswordPolicy(hasNumber, binding.icNumber)
        })

        passwordWatcher.specialChar.observe(context as LifecycleOwner, { hasSpecialChar ->
            displayPasswordPolicy(hasSpecialChar, binding.icSpecialCharacter)
        })

        passwordWatcher.minimumLength.observe(context as LifecycleOwner, { hasMinimumLength ->
            displayPasswordPolicy(hasMinimumLength, binding.icMinimumLength)
        })
    }

    private fun displayStrength(strengthLevel: StrengthLevel) {
        when (strengthLevel) {
            StrengthLevel.WEAK -> {
                binding.pbStrength.progress = 50F
                binding.tvMessageAlert.text =
                    resources.getString(R.string.password_policy_alert_message_weak)
                isCompletePasswordPolicy(false)
                displayPasswordAlert(false, binding.icMessageAlert)
            }
            StrengthLevel.STRONG -> {
                binding.pbStrength.progress = 100F
                binding.tvMessageAlert.text =
                    resources.getString(R.string.password_policy_alert_message_strong)
                isCompletePasswordPolicy(true)
                displayPasswordAlert(true, binding.icMessageAlert)
            }
        }
    }

    private fun displayPasswordAlert(isComplete: Boolean, image: ImageView) {
        if (isComplete) {
            image.setImageResource(R.drawable.ic_check)
        } else {
            image.setImageResource(R.drawable.ic_alert_message)
        }
    }

    private fun displayPasswordPolicy(value: Int, image: ImageView) {
        if (value == 1) {
            image.setImageResource(R.drawable.ic_check)
        } else {
            image.setImageResource(R.drawable.ic_dot)
        }
    }

    fun openTooltip() {
        tooltip = Tooltip.Builder(context)
            .anchor(binding.ibSpecialCharactersInformation, 0, 0, false)
            .text(R.string.tooltip_special_characters)
            .styleId(R.style.ToolTipAltStyle)
            .maxWidth(resources.displayMetrics.widthPixels / 2)
            .arrow(true)
            .closePolicy(getClosePolicy())
            .overlay(false)
            .create()

        tooltip?.dismiss()

        tooltip
            ?.doOnHidden {
                tooltip = null
            }
            ?.doOnFailure { }
            ?.doOnShown {}
            ?.show(binding.ibSpecialCharactersInformation, Tooltip.Gravity.TOP, true)
    }

    private fun getClosePolicy(): ClosePolicy {
        val builder = ClosePolicy.Builder()
        builder.outside(true)
        return builder.build()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        tooltip?.dismiss()
    }

}