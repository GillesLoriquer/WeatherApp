package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.database.ForecastDatabase
import com.example.weatherapp.data.network.*
import com.example.weatherapp.data.repository.ForecastRepository
import com.example.weatherapp.data.repository.ForecastRepositoryImpl
import com.example.weatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        // créer une dépendance de la bdd
        // instance() est retourné par androidXModule et dans ce cas représente le contexte
        bind() from singleton { ForecastDatabase(instance()) }

        // créer une dépendance du dao
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }

        // créer une dépendence pour ConnectivityInterceptor
        // contrairement aux bindings ci-dessous, ConnectivityInterceptor possède une implémentation (on spécifie donc
        // le type de l'interface en instanciant son implémentation avec with singleton)
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }

        // créer une dépendance de ApixuApiService
        // ici instance() fait référence à ConnectivityInterceptor déjà instancié
        bind() from singleton { ApixuApiService(instance()) }

        // créer une dépendance de WeatherNetworkDataSource
        // ici instance() fait référence à ApixuApiService déjà instancié
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }

        // créer une dépendance de ForecastRepository
        // les deux instances font références aux paramètres nécessaire à l'instanciation de ForecastRepositoryImpl
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }

        // créer une instance de CurrentWeatherViewModelFactory
        // ici instance() fait référence à ForecastRepository déjà instancié
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        // Initialisation de la librairie ThreeTenAbp
        AndroidThreeTen.init(this)
    }
}