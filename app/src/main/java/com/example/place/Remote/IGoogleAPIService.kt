package com.example.place.Remote

import com.example.place.Model.MyPlace
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IGoogleAPIService {
    @GET
    fun getNearByPlaces(@Url url:String) : Call<MyPlace>

}