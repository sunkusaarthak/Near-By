package com.example.place.Common

import com.example.place.Remote.IGoogleAPIService
import com.example.place.Remote.RetroFitClient

object Common {


    private val GOOGLE_API_URL = "https://maps.googleapis.com/"
    val googleAPIService:IGoogleAPIService
    get() = RetroFitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}