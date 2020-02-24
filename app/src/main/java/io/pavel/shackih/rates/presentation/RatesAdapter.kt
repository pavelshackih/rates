package io.pavel.shackih.rates.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.pavel.shackih.rates.R
import io.pavel.shackih.rates.domain.Rate
import io.pavel.shackih.rates.utils.getCountryDescription
import io.pavel.shackih.rates.utils.getEmojiByCountryCode
import io.pavel.shackih.rates.utils.parseBigDecimal
import java.text.DecimalFormat

class RatesAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val viewModel: MainViewModel
) : ListAdapter<Rate, RatesViewHolder>(AsyncDifferConfig.Builder(RateDiffUtilCallback).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder =
        RatesViewHolder(
            context,
            viewModel,
            layoutInflater.inflate(R.layout.rate_view_holder, parent, false)
        )

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RatesViewHolder(
    private val context: Context,
    private val viewModel: MainViewModel,
    view: View
) :
    RecyclerView.ViewHolder(view) {

    private lateinit var currentRate: Rate

    private val iconTextView = view.findViewById<TextView>(R.id.country_text_view)
    private val codeTextView = view.findViewById<TextView>(R.id.code_text_view)
    private val descTextView = view.findViewById<TextView>(R.id.desc_text_view)
    private val valueEditText = view.findViewById<EditText>(R.id.value_edit_text)

    init {
        valueEditText.addTextChangedListener(afterTextChanged = { text ->
            if (this.adapterPosition == 0) {
                currentRate.value = text.parseBigDecimal()
                viewModel.onChangeBase(currentRate)
            }
        })
        valueEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (this.adapterPosition > 0) {
                    viewModel.onChangeBase(currentRate)
                }
            }
        }
    }

    fun bind(rate: Rate) {
        currentRate = rate
        codeTextView.text = rate.code
        iconTextView.text = rate.code.getEmojiByCountryCode()
        descTextView.text = context.getCountryDescription(rate.code)
        valueEditText.setText(DECIMAL_FORMAT.format(rate.value))
    }

    companion object {
        private val DECIMAL_FORMAT = DecimalFormat("0.00")
    }
}

object RateDiffUtilCallback : DiffUtil.ItemCallback<Rate>() {

    override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean =
        oldItem.code == newItem.code

    override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean =
        oldItem == newItem
}
