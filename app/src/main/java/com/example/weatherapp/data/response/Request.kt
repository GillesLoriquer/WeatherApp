package com.example.weatherapp.data.response


data class Request(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)