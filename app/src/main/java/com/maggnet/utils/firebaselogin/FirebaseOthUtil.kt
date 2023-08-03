package com.maggnet.utils.firebaselogin

import android.app.Activity
import android.content.ContentValues
import androidx.constraintlayout.widget.Constraints
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.maggnet.ui.extensions.empty
import com.maggnet.ui.extensions.safeGet
import com.maggnet.utils.AppLogger
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseOthUtil @Inject constructor() {

    var loginDataUpdateListener: LoginDataUpdateListener? = null
    var mVerificationId: String = String.empty

    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // verification completed
                AppLogger.d(ContentValues.TAG, "onVerificationCompleted:$credential")
                loginDataUpdateListener?.onUiUpdate(stateSignInSuccess, "", credential, "")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked if an invalid request for verification is made,
                // for instance if the the phone number format is invalid.
                AppLogger.d(ContentValues.TAG, "onVerificationFailed")
                loginDataUpdateListener?.onFailed(stateVerifyFailed, e)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                AppLogger.d(ContentValues.TAG, "onCodeSent:$verificationId")
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                loginDataUpdateListener?.onUiUpdate(stateCodeSent, mVerificationId, null, "")
            }
        }


    fun startPhoneNumberVerification(phoneNumber: String, activity: Activity) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(mCallbacks!!) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, activity: Activity) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    AppLogger.d(Constraints.TAG, "signInWithCredential:success")
                    getToken(credential)
                } else {
                    // Sign in failed, display a message and update the UI
                    AppLogger.d(Constraints.TAG, "signInWithCredential:failure")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        loginDataUpdateListener?.onFailed(stateVerifyFailed, task.exception!!)
                        AppLogger.d(Constraints.TAG, "Invalid code was entered")
                    }
                    // Sign in failed
                }
            }
    }


    private fun getToken(credential: PhoneAuthCredential?) {
        val mUser = FirebaseAuth.getInstance().currentUser
        mUser?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val idToken = task.result!!.token
                loginDataUpdateListener?.onUiUpdate(
                    stateSignInSuccess,
                    "",
                    credential,
                    idToken.safeGet()
                )
            } else {
                loginDataUpdateListener?.onFailed(stateVerifyFailed, task.exception!!)
            }
        }
    }

    companion object {
        const val stateCodeSent = 1
        const val stateSignInSuccess = 2
        const val stateVerifyCompleted = 3
        const val stateVerifyFailed = 4
    }
}