package io.pavel.shackih.rates.data.network

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal
import java.util.*

data class Latest(
    @SerializedName("base")
    var base: String?,
    @SerializedName("date")
    var date: Date?,
    @SerializedName("rates")
    var rates: Map<String, BigDecimal>
)

interface RatesService {

    @GET(LATEST_PATH)
    fun getLatest(@Query("base") base: String): Single<Latest>

    companion object {
        private const val LATEST_PATH = "latest"
    }
}