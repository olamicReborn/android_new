package com.maggnet.utils.firebaselogin

import com.google.firebase.auth.PhoneAuthCredential

interface LoginDataUpdateListener {
    fun onUiUpdate(
        data: Int,
        verificationId: String,
        credential: PhoneAuthCredential?,
        userToken: String
    )

    fun onFailed(data: Int, e: Exception)
}