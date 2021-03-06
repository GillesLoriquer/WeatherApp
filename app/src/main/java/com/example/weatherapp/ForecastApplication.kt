package com.example.weatherapp

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.example.weatherapp.data.database.ForecastDatabase
import com.example.weatherapp.data.network.*
import com.example.weatherapp.data.provider.LocationProvider
import com.example.weatherapp.data.provider.LocationProviderImpl
import com.example.weatherapp.data.provider.UnitProvider
import com.example.weatherapp.data.provider.UnitProviderImpl
import com.example.weatherapp.data.repository.ForecastRepository
import com.example.weatherapp.data.repository.ForecastRepositoryImpl
import com.example.weatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
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

        /************************* BDD & DAO */
        // -------------- ForecastDatabase
        // créer une dépendance de la bdd
        // instance() est retourné par androidXModule et dans ce cas représente le contexte
        bind() from singleton { ForecastDatabase(instance()) }

        // -------------- CurrentWeatherDao
        // créer une dépendance de CurrentWeatherDao
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }

        // -------------- WeatherLocationDao
        // créer une dépendance de WeatherLocationDao
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }

        /************************* Network */
        // -------------- ApixuApiService
        // créer une dépendance de ApixuApiService
        // ici instance() fait référence à ConnectivityInterceptor déjà instancié
        bind() from singleton { ApixuApiService(instance()) }

        // -------------- WeatherNetworkDataSource
        // créer une dépendance de WeatherNetworkDataSource
        // ici instance() fait référence à ApixuApiService déjà instancié
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }

        // -------------- ConnectivityInterceptor
        // créer une dépendence pour ConnectivityInterceptor
        // contrairement aux bindings ci-dessous, ConnectivityInterceptor possède une implémentation (on spécifie donc
        // le type de l'interface en instanciant son implémentation avec with singleton)
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }

        /************************* Repository */
        // -------------- ForecastRepository
        // créer une dépendance de ForecastRepository
        // les deux instances font références aux paramètres nécessaire à l'instanciation de ForecastRepositoryImpl
        bind<ForecastRepository>() with singleton {
            ForecastRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }

        /************************* VM Factory */
        // -------------- CurrentWeatherViewModelFactory
        // créer une instance de CurrentWeatherViewModelFactory
        // ici instance() fait référence à ForecastRepository déjà instancié
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }

        /************************* FusedLocationProviderClient */
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }

        /************************* Providers */
        // -------------- UnitProvider
        // créer une instance de UnitProvider
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }

        // -------------- LocationProvider
        // créer une instance de LocationProvider
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        // Initialisation de la librairie ThreeTenAbp
        AndroidThreeTen.init(this)

        // set les valeurs par défaut des SharedPreferences d'après les defaultValue définies dans preferences.xml
        // ces valeurs sont paramétrées au premier lancement de l'application et ne sont pas réappliquées (readAgain à false)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}