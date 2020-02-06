package io.pavel.shackih.rates.data

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

data class Latest(
    @SerializedName("base")
    var base: String,
    @SerializedName("date")
    var date: LocalDate,
    @SerializedName("rates")
    var rates: Map<String, Double>
)