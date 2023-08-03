package com.maggnet.data.register.fcm.model

import com.google.gson.annotations.SerializedName

data class FcmTokenRequest(@SerializedName("user_id") val userId: String,
                           @SerializedName("device_id") val deviceId: String,
                           @SerializedName("firebase_token") val firebaseToken: String)

