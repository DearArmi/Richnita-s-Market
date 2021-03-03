package com.example.mailbox

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.ClassCastException


private const val LOCATION_PERMISSION_CODE = 1000

class MapsFragment2 : Fragment() {


    private val userLocation = Location("")
    private var mailBoxLocation = Location("")
    var distance = 0.0f


    //Interface
    interface MailBoxListener{
        fun onShowedLocation(distance:Float)
         }

         private lateinit var mailBoxListener: MailBoxListener


         override fun onAttach(context: Context) {
             super.onAttach(context)

             mailBoxListener = try {
                 context as MailBoxListener
             }catch (e: ClassCastException){
                 throw ClassCastException("$context must implement MailBoxListener")
             }
         }

    private val callback = OnMapReadyCallback { googleMap ->


        val mailBox = LatLng(mailBoxLocation.latitude, mailBoxLocation.longitude)
        val userPosition = LatLng(userLocation.latitude, userLocation.longitude)

        //Calculating distance between user and mailbox
        distance = userLocation.distanceTo(mailBoxLocation)

        //Using interface to pass distance
        mailBoxListener.onShowedLocation(distance)

        //Adding markers
        googleMap.addMarker(MarkerOptions().position(mailBox).title("MailBox").snippet("Distance: $distance m").icon(getIcon()))
        googleMap.addMarker(MarkerOptions().position(userPosition).title("User"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 13.0f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_maps2, container, false)

        requestLocationPermission()
        return rootView
    }


    // Requesting Location Permission
    private fun requestLocationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if (checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                getUserLocation()//

            }else{
                val permissionArray = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                requestPermissions(permissionArray, LOCATION_PERMISSION_CODE)
            }
        }else{
            getUserLocation()//
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_CODE){
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                getUserLocation()//
            }else if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)){
                showLocationPermissionDialog()
            }else{
                requireActivity().finish()
            }
        }
    }
    //Showing Permission Dialog
    private fun showLocationPermissionDialog() {
        val dialog = AlertDialog.Builder(requireActivity()).setTitle("Location Permission needed").setMessage("Some Message")
            .setPositiveButton(android.R.string.ok){ _: DialogInterface, _:Int->
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1000)
            }.setNegativeButton(android.R.string.no){ _: DialogInterface, _:Int->
                requireActivity().finish()
            }
        dialog.show()
    }

    //Finding User Location
    @SuppressLint("MissingPermission")
    private fun getUserLocation(){

        val fused = LocationServices.getFusedLocationProviderClient(requireActivity())

        fused.lastLocation.addOnSuccessListener {
                location: Location? ->

            if (location != null){
                userLocation.latitude = location.latitude
                userLocation.longitude = location.longitude
                setUpMap()
            }

        }
    }
    //Setting map after getting userlocation
    private fun setUpMap(){

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    fun setMailBoxLocation(mailBox: MailBox){

        mailBoxLocation.latitude = mailBox.latitude
        mailBoxLocation.longitude = mailBox.longitude

    }
    //Getting Icon for mailbox
    private fun getIcon(): BitmapDescriptor {

        val drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_markunread_mailbox_24px)
        drawable?.setBounds(0,0,drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(drawable?.intrinsicWidth ?: 0, drawable?.intrinsicHeight?: 0, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}