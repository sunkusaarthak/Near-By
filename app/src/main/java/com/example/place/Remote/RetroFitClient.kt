package com.example.place.Remote

import com.google.android.datatransport.runtime.retries.Retries
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitClient {
    private var retrofit : Retrofit?= null
    fun getClient(baseUrll :String) :Retrofit{
        if(retrofit==null)
        {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrll)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}