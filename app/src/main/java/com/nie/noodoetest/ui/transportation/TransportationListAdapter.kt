package com.nie.noodoetest.ui.transportation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.nie.noodoetest.R
import com.nie.noodoetest.data.remote.model.response.government.TransportationInfoItem
import com.nie.noodoetest.databinding.ItemTransportationBinding
import com.nie.noodoetest.extension.toDateString
import com.nie.noodoetest.extension.toGovernmentDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import throttleClick

class TransportationListAdapter : RecyclerView.Adapter<TransportationListAdapter.TransportationListViewHolder>() {

    private val items = mutableListOf<TransportationInfoItem>()

    private val compositeDisposable = CompositeDisposable()

    var onItemClicked = { _: TransportationInfoItem ->  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportationListViewHolder {
        val binding = ItemTransportationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransportationListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransportationListViewHolder, position: Int) {
        holder.bind(items[position], compositeDisposable, onItemClicked)
    }

    override fun getItemCount() = items.size

    fun addAll(list: List<TransportationInfoItem>) {
        items.clear()
        items.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }

    fun clear() = compositeDisposable.clear()

    class TransportationListViewHolder(private val binding: ItemTransportationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TransportationInfoItem, compositeDisposable: CompositeDisposable, onItemClicked: (TransportationInfoItem) -> Unit) {
            binding.textTitle.text = item.chtmessage
            binding.textContent.text = item.content
            binding.textUpdateDate.text = binding.root.context.getString(R.string.update_time).format(item.updatetime.toGovernmentDate().toDateString())

            RxView.clicks(binding.root)
                .throttleClick()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onItemClicked.invoke(item)
                }.addTo(compositeDisposable)
        }
    }
}