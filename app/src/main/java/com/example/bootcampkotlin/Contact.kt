package com.example.bootcampkotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// vai representar cada contato da nossa lista - com 3 atributos
// já torna essa classe apta, para ser trafegada entre essas telas.
@Parcelize
data class Contact (
    var name: String,
    var phone: String,
    var photograph: String
    ) : Parcelable