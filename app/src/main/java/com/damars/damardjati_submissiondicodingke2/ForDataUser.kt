package com.damars.damardjati_submissiondicodingke2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForDataUser (
    var thisUsername: String? = null,
    var thisName: String? = null,
    var thisCompany: String? = null,
    var thisAvatar: String? = null,
    var thisLocation: String? = null,
    var thisRepository: String? = null,
    var thisFollower: String? = null,
    var thisFollowing: String? =null
): Parcelable
