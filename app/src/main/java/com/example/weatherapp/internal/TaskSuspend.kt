package com.example.weatherapp.internal

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

fun <T> Task<T>.asDeferred(): Deferred<T> {
    val completableDeferred = CompletableDeferred<T>()

    this.addOnSuccessListener {
        completableDeferred.complete(it)
    }

    this.addOnFailureListener {
        completableDeferred.completeExceptionally(it)
    }

    return completableDeferred
}