package io.pavel.shackih.rates.data.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer : JsonDeserializer<Date> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date? = json?.let { FORMATTER.parse(it.asString) }

    companion object {
        @JvmStatic
        private val FORMATTER = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    }
}