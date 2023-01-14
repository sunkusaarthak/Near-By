package com.example.place

class CorrectLatLong {
    fun test(lat : String, lan : String) : Boolean {
        val regex = "^([-+]?//d{1,2}[.]//d+),//s*([-+]?//d{1,3}[.]//d+)/$".toRegex()
        return (regex.containsMatchIn(lat) and regex.containsMatchIn(lan));
    }
}