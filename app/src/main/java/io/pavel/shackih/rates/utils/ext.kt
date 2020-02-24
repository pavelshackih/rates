@file:JvmName("AppUtils")
@file:Suppress("NOTHING_TO_INLINE")

package io.pavel.shackih.rates.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import io.pavel.shackih.rates.App
import io.pavel.shackih.rates.di.AppComponent
import java.math.BigDecimal

inline fun Context.getAppComponent(): AppComponent = (this.applicationContext as App).appComponent

inline fun Context.getCountryDescription(code: String): String {
    val resId = resources.getIdentifier(
        code,
        "string",
        packageName
    )
    return if (resId == 0) "" else getString(resId)
}

fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networks = cm.allNetworks
        for (network in networks) {
            val capabilities = cm.getNetworkCapabilities(network) ?: continue
            if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            ) {
                return true
            }
        }
    } else {
        @Suppress("DEPRECATION")
        return cm.activeNetworkInfo?.isConnected ?: false
    }
    return false
}

inline fun Editable?.parseBigDecimal(): BigDecimal =
    if (isNullOrEmpty()) BigDecimal.ZERO else toString().toBigDecimal()

inline fun String.getEmojiByCountryCode(): String {
    val firstLetter = Character.codePointAt(this, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(this, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}
