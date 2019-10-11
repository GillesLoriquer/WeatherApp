package com.example.weatherapp.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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

        // couple CurrentWeatherViewModel à l'objet binding pour que puisse être exploité le viewModel dans
        // current_weather_fragment.xml
        binding.viewModel = ViewModelProviders
            .of(this, currentWeatherViewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        return binding.root
    }
}
