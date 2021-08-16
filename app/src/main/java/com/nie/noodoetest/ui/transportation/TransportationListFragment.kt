package com.nie.noodoetest.ui.transportation

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.nie.noodoetest.R
import com.nie.noodoetest.base.BaseFragment
import com.nie.noodoetest.bean.Constant
import com.nie.noodoetest.databinding.FragmentTransportationListBinding
import com.nie.noodoetest.extension.addFragment
import com.nie.noodoetest.ui.modifytimezone.ModifyTimeZoneFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import throttleClick

class TransportationListFragment : BaseFragment() {

    companion object {
        fun newInstance() = TransportationListFragment()
    }

    private lateinit var binding: FragmentTransportationListBinding

    override val viewModel by viewModel<TransportationListViewModel>()

    private val adapter by inject<TransportationListAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleBackPress()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTransportationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchTransportationInfo()

        initView()
        setOnClickListener()
        observableLiveData()
    }

    override fun onDestroy() {
        adapter.clear()
        super.onDestroy()
    }

    private fun initView() {
        binding.recyclerTransportation.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerTransportation.adapter = adapter

        (activity as AppCompatActivity?)?.setSupportActionBar(binding.toolBar)
        (activity as AppCompatActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolBar.setNavigationOnClickListener {
            activity?.finish()
        }
    }

    private fun setOnClickListener() {
        adapter.onItemClicked = {
            if (it.url != null && it.url.isNotEmpty()) {
                openBrowser(it.url)
            }
        }

        RxView.clicks(binding.textTimezone)
            .throttleClick()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                childFragmentManager.addFragment(R.id.container, ModifyTimeZoneFragment.newInstance())
            }.addTo(compositeDisposable)
    }

    private fun observableLiveData() {
        viewModel.transportationInfo.observe(viewLifecycleOwner, {
            adapter.addAll(it.news)
        })
    }

    private fun openBrowser(url: String) {
        val colorInt = Color.parseColor("#000000")
        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(colorInt)
            .build()

        val builder = CustomTabsIntent.Builder()
        builder.setDefaultColorSchemeParams(defaultColors)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun handleBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
    }
}