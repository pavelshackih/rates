package io.pavel.shackih.rates.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.pavel.shackih.rates.R

class ErrorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.error_fragment, container, false)

        val textView = root.findViewById<TextView>(R.id.error_text_view)
        val message = arguments?.getString(MESSAGE)
        message?.let {
            textView.text = it
        }

        val retryButton = root.findViewById<Button>(R.id.retry_button)
        retryButton.setOnClickListener {
            val hostActivity = activity as? HostActivity
            hostActivity?.onRetry()
        }
        return root
    }

    companion object {
        private const val MESSAGE = "message"

        @JvmStatic
        fun newInstance(message: String? = null): ErrorFragment = ErrorFragment().apply {
            arguments = Bundle().apply {
                putString(MESSAGE, message)
            }
        }
    }
}