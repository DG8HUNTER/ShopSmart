package com.example.locationn.ui.theme

import android.location.Address
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

 class MainActivityViewModel {

     private val _longitude: MutableState<Double> = mutableStateOf(0.0)

     val longitude: State<Double> = _longitude


     private val _latitude: MutableState<Double> = mutableStateOf(0.0)

     val latitude: State<Any> = _latitude

     private val _address: MutableState<String?> = mutableStateOf(null)

     val address: State<String?> = _address


     fun setValue(newValue: Any?, name: String) {
         when (name) {
             "longitude" -> if (newValue != null) _longitude.value = newValue as Double
             "latitude" -> if (newValue != null) _latitude.value = newValue as Double
             "address" -> if (newValue != null) _address.value = newValue.toString()

         }
     }
 }