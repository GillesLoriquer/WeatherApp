package com.example.weatherapp.data

import com.example.weatherapp.data.response.CurrentWeatherResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://api.weatherstack.com/"
private const val API_KEY = "0533f6d3903b63c4102e3023b2afc446"

interface ApixuApiService {
    @GET("current")
    suspend fun getCurrentWeather(
        @Query("query") location: String,
        @Query("lang") languageCode: String = "fr"
    ): CurrentWeatherResponse
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Interceptor from OkHttp3 observes, modifies, and potentially short-circuits requests going out and
// the corresponding responses coming back in. Typically interceptors add, remove, or transform headers
// on the request or response
// Used here to add the URL parameter access_key to the URL
private val requestInterceptor = Interceptor {
    val url = it.request()
        .url()
        .newBuilder()
        .addQueryParameter("access_key", API_KEY)
        .build()

    val request = it.request()
        .newBuilder()
        .url(url)
        .build()

    return@Interceptor it.proceed(request)
}

// Building the client with the predefined interceptor
private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(requestInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

// Singleton ApixuApi
object ApixuApi {
    val retrofitService: ApixuApiService by lazy { retrofit.create(ApixuApiService::class.java) }
}