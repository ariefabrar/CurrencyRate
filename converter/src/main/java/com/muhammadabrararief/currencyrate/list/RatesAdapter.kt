package com.muhammadabrararief.currencyrate.list

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.muhammadabrararief.currencyrate.R
import kotlinx.android.synthetic.main.item_rate.view.*
import java.util.*
import javax.inject.Inject

class RatesAdapter (private val glide: RequestManager) :
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

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            @Suppress("UNCHECKED_CAST")
            val combinedChange = createCombinedPayload(payloads as List<Change<Rate>>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData

            if (holder.etCurrency.isFocused) return

            if (oldData.amount != newData.amount) {
                if (newData.amount == 0.0) {
                    holder.etCurrency.setText("")
                } else {
                    val digitCount =
                        Currency.getInstance(newData.currencyCode).defaultFractionDigits
                    holder.etCurrency.setText(
                        String.format(
                            Locale.ROOT,
                            "%.${digitCount}f",
                            newData.amount
                        )
                    )
                }
            }
        }
    }

    inner class ListViewHolder(itemView: View, private val listener: Listener?) :
        RecyclerView.ViewHolder(itemView) {

        val etCurrency: EditText = itemView.etCurrency

        init {
            with(itemView) {
                setOnClickListener {
                    etCurrency.requestFocus()
                }
            }

            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val item = getItem(adapterPosition)
                    val baseRate = if (s.isNullOrEmpty()) {
                        Rate(item.currencyCode, 0.0)
                    } else {
                        Rate(item.currencyCode, s.toString().toDouble())
                    }
                    listener?.onBaseRateChanged(adapterPosition, baseRate)
                }

            }

            with(itemView.etCurrency) {
                setOnFocusChangeListener { _, isFocused ->
                    if (isFocused) {
                        listener?.onBaseRateChanged(adapterPosition, getItem(adapterPosition))
                        setSelection(text.length)
                        addTextChangedListener(textWatcher)
                    } else {
                        removeTextChangedListener(textWatcher)
                    }
                }
            }
        }

        fun bind(item: Rate) = with(itemView) {
            tvCurrencyShort.text = item.currencyCode
            tvCurrencyLong.text = Currency.getInstance(item.currencyCode).displayName
            if (item.amount == 0.0) {
                etCurrency.setText("")
            } else {
                val digitCount = Currency.getInstance(item.currencyCode).defaultFractionDigits
                etCurrency.setText(String.format(Locale.ROOT, "%.${digitCount}f", item.amount))
            }
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

        override fun getChangePayload(oldItem: Rate, newItem: Rate): Any? {
            return Change(oldItem, newItem)
        }

    }

    private fun <Rate> createCombinedPayload(payloads: List<Change<Rate>>): Change<Rate> {

        val firstChange = payloads.first()
        val lastChange = payloads.last()

        return Change(firstChange.oldData, lastChange.newData)
    }

}

data class Change<Rate>(
    val oldData: Rate,
    val newData: Rate
)

