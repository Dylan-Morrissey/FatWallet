package org.wit.fatpredictor.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PredictModel(
    var id: Long =0,
    var weight: String = "",
    var height: String = "",
    var image: String = ""
    ) : Parcelable