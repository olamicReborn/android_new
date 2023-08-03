package com.maggnet.domain.rxcallback

import com.maggnet.domain.remote.BaseError


interface ResponseCallback<T> {
    fun onResponseSuccess(response: T)

    fun onResponseError(error: String)

    fun onNetworkError(error: String)

    fun onServerError(error: String)

    fun onConnectivityError()
}

interface OptimizedResponseCallBack<T>{
    fun onApiSuccess(response: T)
    fun onApiError(error: BaseError)
}