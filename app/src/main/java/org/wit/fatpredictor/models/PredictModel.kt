package org.wit.fatpredictor.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PredictModel(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                        var weight: String = "",
                        var height: String = "",
                        var image: String = "",
                        var age : Int = 0,
                        var gender: Char = 'M',
                        var bodyfat: String = "0-0") : Parcelable



