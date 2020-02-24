package io.pavel.shackih.rates.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.pavel.shackih.rates.R
import java.net.UnknownHostException

class MainActivity : AppCompatActivity(), HostActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.content_view, MainFragment(), null)
                .commit()
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onError(throwable: Throwable) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.content_view,
                ErrorFragment.newInstance(getUiMessageFrom(throwable)),
                null
            )
            .commit()
    }

    override fun onRetry() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_view, MainFragment(), null)
            .commit()
    }

    private fun getUiMessageFrom(throwable: Throwable): String? {
        return when (throwable) {
            is UnknownHostException -> getString(R.string.network_error)
            else -> null
        }
    }
}
