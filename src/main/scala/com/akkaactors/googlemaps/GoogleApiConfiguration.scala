package com.akkaactors.googlemaps

import com.akkaactors.db.util.Config
import com.google.maps.model.{ DistanceMatrix, TravelMode }
import com.google.maps.{ DistanceMatrixApi, GeoApiContext }

object GoogleApiConfiguration extends Config with App {

  val context = new GeoApiContext.Builder()
    .apiKey(googleMapCredentials)
    .build()

  val origins = "San Francisco"
  val destinations = "Seattle"

  val result: DistanceMatrix = DistanceMatrixApi.newRequest(context)
    .origins(origins)
    .destinations(destinations)
    .mode(TravelMode.DRIVING)
    .language("en-EN")
    .await()

  println(result)
}