package com.nie.noodoetest.ui.modifytimezone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nie.noodoetest.base.BaseFragment
import com.nie.noodoetest.databinding.FragmentModifyTimeZoneBinding
import com.nie.noodoetest.databinding.FragmentTransportationListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ModifyTimeZoneFragment : BaseFragment() {

    companion object {
        fun newInstance() = ModifyTimeZoneFragment()
    }

    private lateinit var binding: FragmentModifyTimeZoneBinding

    override val viewModel by viewModel<ModifyTimezoneViewModel>()

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

        initView()
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
    }

    private fun handleBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitPage()
            }
        })
    }

    private fun exitPage() {
        parentFragment?.childFragmentManager?.popBackStack()
    }
}