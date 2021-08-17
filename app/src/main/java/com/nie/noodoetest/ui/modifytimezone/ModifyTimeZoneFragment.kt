package com.nie.noodoetest.ui.modifytimezone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.nie.noodoetest.R
import com.nie.noodoetest.base.BaseFragment
import com.nie.noodoetest.bean.Constant
import com.nie.noodoetest.databinding.FragmentModifyTimeZoneBinding
import com.nie.noodoetest.ui.login.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import throttleClick

class ModifyTimeZoneFragment : BaseFragment() {

    companion object {
        fun newInstance() = ModifyTimeZoneFragment()
    }

    private lateinit var binding: FragmentModifyTimeZoneBinding

    override val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleBackPress()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentModifyTimeZoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEmail()
        viewModel.getTimeZone()

        initView()
        setOnClickListener()
        observableLiveData()
    }

    private fun initView() {
        (activity as AppCompatActivity?)?.setSupportActionBar(binding.toolBar)
        (activity as AppCompatActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        binding.toolBar.setNavigationOnClickListener {
            exitPage()
        }

        setSpinnerView()
    }

    private fun setSpinnerView() {
        val timeZones = mutableListOf<Int>()
        for (i in 1..10) {
            timeZones.add(i)
        }

        val arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, timeZones)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun setOnClickListener() {
        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val timeZone = parent.getItemAtPosition(position)
            binding.autoCompleteTextView.hint = timeZone.toString()

            Log.d(Constant.TAG, "timeZone = ${timeZone as Int}")
            viewModel.modifyTimeZone(timeZone)
        }

        RxView.clicks(binding.textSignOut)
            .throttleClick()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.signOut()
            }.addTo(compositeDisposable)
    }

    private fun observableLiveData() {
        viewModel.email.observe(viewLifecycleOwner, {
            binding.textEmail.text = it
        })

        viewModel.modifyEmailSuccess.observe(viewLifecycleOwner, {
            if (it.not()) {
                requireContext().toast(R.string.server_error)
                return@observe
            }

            requireContext().toast(R.string.update_time_zone_success)
        })

        viewModel.timeZone.observe(viewLifecycleOwner, {
            binding.autoCompleteTextView.setText(it.toString(), false)
        })
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitPage()
            }
        })
    }

    private fun exitPage() {
        parentFragment?.childFragmentManager?.popBackStack()
    }
}