package com.nie.noodoetest.ui.login

import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.nie.noodoetest.R
import com.nie.noodoetest.base.BaseActivity
import com.nie.noodoetest.databinding.ActivityLoginBinding
import com.nie.noodoetest.extension.addFragment
import com.nie.noodoetest.extension.hideSoftKeyboard
import com.nie.noodoetest.ui.transportation.TransportationListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import throttleClick

class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override val viewModel by viewModel<LoginViewModel>()

    private var isVisiblePassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.isLoggedIn()

        setOnClickListener()
        setViewsTouchListener()
        observableLiveData()
    }

    private fun setOnClickListener() {
        RxView.clicks(binding.textLogin)
            .throttleClick()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val userName = binding.editTextUserName.text.toString()
                val password = binding.editTextPassword.text.toString()

                if (userName.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.login(userName, password)
                } else {
                    toast(R.string.login_field_is_empty)
                }
            }.addTo(compositeDisposable)
    }

    private fun setViewsTouchListener() {
        binding.editTextPassword.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.editTextPassword.right - binding.editTextPassword.compoundDrawables[2].bounds.width())) {
                    isVisiblePassword = isVisiblePassword.not()

                    val drawable = if (isVisiblePassword) R.drawable.ic_baseline_visibility_24 else R.drawable.ic_baseline_visibility_off_24
                    binding.editTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable, 0)
                    binding.editTextPassword.inputType = if (isVisiblePassword) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD else (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                }
            }

            return@setOnTouchListener false
        }
    }

    private fun observableLiveData() {
        viewModel.loginSuccess.observe(this, {
            if (it.not()) {
                binding.container.visibility = View.GONE
                return@observe
            }

            binding.container.visibility = View.VISIBLE

            hideSoftKeyboard()
            supportFragmentManager.addFragment(R.id.container, TransportationListFragment.newInstance(), isSlide = false)
        })
    }
}