package com.example.unsplashappsjetpack

import com.example.unsplashappsjetpack.entity.NetworkResult
import com.example.unsplashappsjetpack.network.NetworkInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotoRepository(private val scope: CoroutineScope) {
    private val instance = NetworkInstance.create()

    private val _photo = MutableStateFlow(emptyList<NetworkResult>())
    private var page = 1
    val photos: StateFlow<List<NetworkResult>> get() = _photo

    fun getPhotos() = scope.launch {
        val addLoading = _photo.value.toMutableList()
        addLoading.add(NetworkResult.Loading)
        try {
            delay(2000)
            val photo = instance.photos(page)
            val added = _photo.value.toMutableList()
            added.addAll(photo)
            _photo.value = added
            page += 1
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}