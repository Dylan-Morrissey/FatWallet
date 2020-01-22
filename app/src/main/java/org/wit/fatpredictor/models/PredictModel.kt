package org.wit.fatpredictor.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PredictModel(var id: Long = 0,
                          var weight: String = "",
                          var height: String = "",
                          var image: String = "",
                          var age : Int = 0,
                          var gender: Char = 'M',
                          var bodyfat: String = "0-0") : Parcelable



