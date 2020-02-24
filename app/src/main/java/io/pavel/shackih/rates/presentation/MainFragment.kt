package io.pavel.shackih.rates.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import io.pavel.shackih.rates.R
import io.pavel.shackih.rates.domain.RatesInteractor
import io.pavel.shackih.rates.rx.RxSchedulers
import io.pavel.shackih.rates.utils.getAppComponent

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.main_fragment, container, false)

        val component = activity?.getAppComponent() ?: error("Can't get AppComponent from context")
        viewModel =
            ViewModelProvider(
                this,
                AppViewModelFactory(component.rxSchedulers(), component.ratesInteractor())
            )[MainViewModel::class.java]

        lifecycle.addObserver(viewModel)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_view)
        recycler.itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
        val adapter = RatesAdapter(requireContext(), inflater, viewModel)
        recycler.adapter = adapter

        viewModel.rates.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            val hostActivity = activity as? HostActivity
            hostActivity?.onError(it)
        })

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

    private class AppViewModelFactory(
        private val rxSchedulers: RxSchedulers,
        private val ratesInteractor: RatesInteractor
    ) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == MainViewModel::class.java) {
                return MainViewModel(rxSchedulers, ratesInteractor) as T
            } else {
                error("Wrong model type for AppViewModelFactory: $modelClass")
            }
        }
    }
}
