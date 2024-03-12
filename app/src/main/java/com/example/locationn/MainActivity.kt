package com.example.locationn

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.locationn.ui.theme.LocationnTheme
import com.example.locationn.ui.theme.MainActivityViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationnTheme {
                fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
                val mainActivityViewModel =MainActivityViewModel()
                val context= LocalContext.current
                // A surface container using the 'background' color from the theme
                Surface(

                ) {

                    Column() {
                      //  Row() {

                       //     Text(text = "Longitude : ")
                         //  Text(text = "${mainActivityViewModel.longitude.value}")
                      //  }

                       // Row() {

                        //    Text(text = "Latitude : ")
                        //    Text(text = "${mainActivityViewModel.latitude.value}")
                        //}

                        Row(){
                            Text("Address : ")
                            Text(text="${mainActivityViewModel.address.value}")
                        }

                        Button(onClick = { getLocation(fusedLocationProviderClient , mainActivityViewModel , context) } , modifier = Modifier
                            .size(150.dp)
                           ) {

                            Text(text = "Get Location " , fontWeight = FontWeight.Bold , color = Color.Black )

                        }

                        if(mainActivityViewModel.latitude.value!=0.0  && mainActivityViewModel.longitude.value!=0.0){
                            AddressFromCoordinates(mainActivityViewModel.latitude.value as Double,
                                mainActivityViewModel.longitude.value as Double ,
                                context,
                                mainActivityViewModel
                            )

                        }






                    }
                }

                }
            }
        }
    }

private fun getLocation(
    fusedLocationProviderClient: FusedLocationProviderClient,
    mainActivityViewModel: MainActivityViewModel,
    context: Context,

    ){
  //Check location permission
    if(ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION)
   != PackageManager.PERMISSION_GRANTED  &&
        ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION) , 100)
        return
    }
    val location = fusedLocationProviderClient.lastLocation
    location.addOnSuccessListener {
        if(it!=null){
            mainActivityViewModel.setValue(it.longitude , "longitude")
            mainActivityViewModel.setValue(it.latitude , "latitude")



        }
    }


}

@Composable
fun AddressFromCoordinates(
    latitude: Double,
    longitude: Double,
    context: Context,
    mainActivityViewModel: MainActivityViewModel
) {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)

    Column(modifier = Modifier.padding(16.dp)) {
        if (addresses.isEmpty()) {
            Text(text = "No address found for the provided coordinates.")
            // You can retrieve other address details as needed
        } else {

            //Text(text = "Address:")
            // Text(text = addresses[0].getAddressLine(0))
            mainActivityViewModel.setValue(addresses[0].getAddressLine(0), "address")
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name")


    }

}

