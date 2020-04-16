package com.muhammadabrararief.currencyrate.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.muhammadabrararief.currencyrate.R
import kotlinx.android.synthetic.main.item_rate.view.*
import java.util.*

class RatesAdapter(private val glide: RequestManager) :
    ListAdapter<Rate, RatesAdapter.ListViewHolder>(RateItemDC()) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_rate,
            parent,
            false
        ), listener
    )


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(getItem(position))


    inner class ListViewHolder(itemView: View, private val listener: Listener?) :
        RecyclerView.ViewHolder(itemView) {

        init {
            with(itemView) {
                setOnClickListener {
                    etCurrency.requestFocus()
                }
            }

            with(itemView.etCurrency) {
                setOnFocusChangeListener { _, isFocused ->
                    if (isFocused) {
                        if (adapterPosition != 0) {
                            listener?.onBaseRateChanged(adapterPosition, getItem(adapterPosition))
                            setSelection(text.length)
                        }
                    }
                }
            }
        }

        fun bind(item: Rate) = with(itemView) {
            tvCurrencyShort.text = item.currencyCode
            tvCurrencyLong.text = Currency.getInstance(item.currencyCode).displayName
            etCurrency.setText((String.format("%.2f", item.amount)))
            glide.load(item.getFlagUrl()).apply(RequestOptions.circleCropTransform())
                .into(ivCurrencyThumbnail)

            ViewCompat.setTransitionName(tvCurrencyShort, item.currencyCode)
            ViewCompat.setTransitionName(
                tvCurrencyLong,
                Currency.getInstance(item.currencyCode).displayName
            )
            ViewCompat.setTransitionName(ivCurrencyThumbnail, item.getFlagUrl())
        }

    }

    interface Listener {
        fun onBaseRateChanged(
            position: Int,
            rate: Rate
        )
    }

    private class RateItemDC : DiffUtil.ItemCallback<Rate>() {

        override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
            return oldItem.currencyCode == newItem.currencyCode
        }

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
            return oldItem == newItem
        }

    }

}