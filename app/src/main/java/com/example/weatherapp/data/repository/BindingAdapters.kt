package com.example.weatherapp.data.repository

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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
        textView.text = "${it.roundToInt()} °C"
    }
}

@BindingAdapter("temperatureRessentie")
fun setTemperatureRessentie(textView: TextView, temperatureRessentie: Double?) {
    temperatureRessentie?.let {
        textView.text = "Ressenti ${it.roundToInt()} °C"
    }
}

@BindingAdapter("vitesseVent")
fun setVitesseVent(textView: TextView, vitesseVent: Double?) {
    vitesseVent?.let {
        textView.text = "${it.roundToInt()} km/h"
    }
}

@BindingAdapter("pressionAtmo")
fun setPressionAtmo(textView: TextView, pressionAtmo: Double?) {
    pressionAtmo?.let {
        textView.text = "${it.roundToInt()} hPa"
    }
}

@BindingAdapter("precipitation")
fun setPrecipitation(textView: TextView, precipitation: Double?) {
    precipitation?.let {
        textView.text = "$it mm"
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
        textView.text = "$it km"
    }
}