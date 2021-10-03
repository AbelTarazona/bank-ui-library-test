package com.abeltarazona.password_ui

import android.os.Bundle
import com.abeltarazona.password_ui.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.passwordPolicy.also {
            it.configure(binding.etPassword)
            it.setCallback { isCompletePasswordPolicy ->
                Timber.d(isCompletePasswordPolicy.toString())
                binding.btnCreate.isEnabled = isCompletePasswordPolicy
            }
        }
    }
}