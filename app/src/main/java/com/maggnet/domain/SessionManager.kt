package com.maggnet.domain

import javax.inject.Inject

class SessionManager @Inject constructor() {

    private var sessionExpiryListeners = mutableListOf<SessionExpired>()
    var userLoggedOut = false

    fun appTokenExpired() {
        sessionExpiryListeners.forEach {
            if (userLoggedOut.not())
                it.onAppTokenExpired()
        }
    }

    fun unSubscribe(sessionExpired: SessionExpired) {
        sessionExpiryListeners.remove(sessionExpired)
    }

    fun subscribe(sessionExpired: SessionExpired) {
        if (sessionExpiryListeners.contains(sessionExpired).not())
            sessionExpiryListeners.add(sessionExpired)
    }

    interface SessionExpired {
        fun onAppTokenExpired()
    }
}