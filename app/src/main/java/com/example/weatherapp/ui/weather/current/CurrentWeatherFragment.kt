package com.example.weatherapp.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.databinding.CurrentWeatherFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : Fragment(), KodeinAware {

    // Android components can be thought as layers. For example, a View defines a layer, on top of an Activity layer,
    // itself on top of the Application layer.
    // The closestKodein function will always return the kodein of the closest parent layer. In a View or a Fragment,
    // for example, it will return the containing Activity’s Kodein, if it defines one, else it will return the
    // "global" Application Kodein.
    override val kodein by closestKodein()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // inflate l'objet binding à partir de sa vue (current_weather_fragment.xml)
        val binding = CurrentWeatherFragmentBinding.inflate(inflater)

        // set ce fragment comme étant le propriétaire du cycle de vie de l'objet binding
        binding.lifecycleOwner = this

        // récupère l'instance de CurrentWeatherViewModelFactory via Kodein
        val currentWeatherViewModelFactory: CurrentWeatherViewModelFactory by instance()

        // créer une instance du view model
        val viewModel = ViewModelProviders
            .of(this, currentWeatherViewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        // couple CurrentWeatherViewModel à l'objet binding pour que puisse être exploité le viewModel dans
        // current_weather_fragment.xml
        binding.viewModel = viewModel

        // affiche "Aujourd'hui" dans le subtitle de l'action bar
        updateDateToToday()

        // observe weatherLocation pour mettre à jour le nom de la location dans l'action bar
        viewModel.weatherLocation.observe(this@CurrentWeatherFragment, Observer { weatherLocation ->
            weatherLocation?.let {
                updateLocation(it.name)
            }
        })

        return binding.root
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        val context = this@CurrentWeatherFragment.activity?.applicationContext
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = context?.getString(R.string.today)
    }
}
