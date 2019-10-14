package com.example.weatherapp.internal

import android.content.Context
import android.preference.PreferenceManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.weatherapp.data.provider.UNIT_SYSTEM
import kotlin.math.roundToInt

/**
 * Binding adapter used to display images from URL using Glide
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context).load(it).into(imageView)
    }
}

@BindingAdapter("temperature")
fun setTemperature(textView: TextView, temperature: Double?) {
    temperature?.let {
        textView.text = when (getUnitSystem(textView.context.applicationContext)) {
            UnitSystem.METRIC -> "${it.roundToInt()} °C"
            else -> "${it.roundToInt()} °F"
        }
    }
}

@BindingAdapter("temperatureRessentie")
fun setTemperatureRessentie(textView: TextView, temperatureRessentie: Double?) {
    temperatureRessentie?.let {
        textView.text = when (getUnitSystem(textView.context.applicationContext)) {
            UnitSystem.METRIC -> "Ressenti ${it.roundToInt()} °C"
            else -> "Ressenti ${it.roundToInt()} °F"
        }
    }
}

@BindingAdapter("vitesseVent")
fun setVitesseVent(textView: TextView, vitesseVent: Double?) {
    vitesseVent?.let {
        textView.text = when (getUnitSystem(textView.context.applicationContext)) {
            UnitSystem.METRIC -> "${it.roundToInt()} km/h"
            else -> "${it.roundToInt()} mi/h"
        }
    }
}

@BindingAdapter("pressionAtmo")
fun setPressionAtmo(textView: TextView, pressionAtmo: Double?) {
    pressionAtmo?.let {
        textView.text = "${it.roundToInt()} mb"
    }
}

@BindingAdapter("precipitation")
fun setPrecipitation(textView: TextView, precipitation: Double?) {
    precipitation?.let {
        textView.text = when (getUnitSystem(textView.context.applicationContext)) {
            UnitSystem.METRIC -> "$it mm"
            else -> "$it in"
        }
    }
}

@BindingAdapter("humidite")
fun setHumidite(textView: TextView, humidite: Double?) {
    humidite?.let {
        textView.text = "${it.roundToInt()} %"
    }
}

@BindingAdapter("couvNuageuse")
fun setCouvNuageuse(textView: TextView, couvNuageuse: Int?) {
    couvNuageuse?.let {
        textView.text = "$it %"
    }
}

@BindingAdapter("visibilite")
fun setVisibilite(textView: TextView, visibilite: Int?) {
    visibilite?.let {
        textView.text = when (getUnitSystem(textView.context.applicationContext)) {
            UnitSystem.METRIC -> "$it km"
            else -> "$it mi"
        }
    }
}

// retourne le type de UnitSystem pour afficher les bonnes unités pour chaque valeur
fun getUnitSystem(appContext: Context): UnitSystem {
    val preferences = PreferenceManager.getDefaultSharedPreferences(appContext)
    val selectedUnitSystem = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.system)
    return UnitSystem.valueOf(selectedUnitSystem!!)
}